import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.concurrent.TimeUnit;

public class Database {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://194.47.178.142:3306/1ik173v23-4";
        String username = "tb222zg";
        String password = "tb222zg";
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }


    public List<Users> listOfUsers() throws SQLException, ClassNotFoundException {

        List<Users> listOfUsers = new ArrayList<>();

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "select * from UserDB";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fName = resultSet.getString("fName");
            String lName = resultSet.getString("lName");
            int titleId = resultSet.getInt("titleId");
            int sNum = resultSet.getInt("sNum");
            boolean isSuspended = resultSet.getBoolean("isSuspended");
            int warnings = resultSet.getInt("warnings");

            Users user = new Users(id, fName, lName, titleId, sNum, isSuspended, warnings);
            listOfUsers.add(user);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return listOfUsers;

    }

    public List<Books> listOfBooks() throws SQLException, ClassNotFoundException {

        List<Books> listOfBooks = new ArrayList<>();

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "select * from BooksDB";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int isbn = resultSet.getInt("isbn");
            String title = resultSet.getString("title");
            int available = resultSet.getInt("available");
            int onLoan = resultSet.getInt("onLoan");

            Books books = new Books(title, isbn, available, onLoan);
            listOfBooks.add(books);
        }
        resultSet.close();
        statement.close();
        connection.close();

        return listOfBooks;

    }

    public boolean addNewUser(Users user) throws SQLException, ClassNotFoundException{


        Connection connection = getConnection();
        String query = "INSERT INTO UserDB (fName,lName,titleId,sNum,warnings,isSuspended,suspentionCount) values (?,?,?,?,0,0,0)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getfName());
        preparedStatement.setString(2, user.getlName());
        preparedStatement.setInt(3, user.getTitleId());
        preparedStatement.setInt(4, user.getsNum());
        int rowsInserted = preparedStatement.executeUpdate();


        if (rowsInserted <= 0) {
            return false;
        }

        preparedStatement.close();
        connection.close();

        return true;
    }

    public List<Loan> listOfLoans() throws SQLException, ClassNotFoundException {
        List<Loan> listOfLoans = new ArrayList<>();

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "select * from Loans";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("userId");
            Date date = resultSet.getDate("date");
            int isbn = resultSet.getInt("isbn");
            int loanId = resultSet.getInt("loan_id");


            Loan loan = new Loan(id, isbn, date, loanId);
            listOfLoans.add(loan);
        }

        resultSet.close();
        statement.close();
        connection.close();


        return listOfLoans;
    }

    public boolean deleteUser(Users u) throws SQLException, ClassNotFoundException {
        boolean deleted = false;

        int id = u.getId();

        Connection connection = getConnection();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            updateBookAmount(id);

            stmt = connection.prepareStatement("DELETE FROM Loans WHERE userId = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();

            stmt = connection.prepareStatement("DELETE FROM UserDB WHERE id = ?");
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();


            if (rowsAffected > 0) {
                deleted = true;
                }

        } catch (SQLException e) {
            System.out.println("Error deleting user from database: " + e.getMessage());
            throw e;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing statement: " + e.getMessage());
                throw e;
            }

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing result set: " + e.getMessage());
                throw e;
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
                throw e;
            }
        }

        return deleted;
    }

    public int userLoanLimit(int id) throws SQLException, ClassNotFoundException {
        int amount = 0;
        List<Users> usersList = listOfUsers();

        Users user = new Users();

        for (Users u : usersList) {
            if (u.getId() == id) {
                user.setTitleId(u.getTitleId());
            }
        }

        switch (user.getTitleId()) {
            case 2 -> amount = 3;
            case 3 -> amount = 5;
            case 4 -> amount = 7;
            case 5 -> amount = 10;
            default ->
                    System.out.println("User does not exist or is admin"); //denna gör inget, men tänker att det är log case senare. Kanske kan göra exception
        }

        return amount;
    }

    public boolean isSuspendedStatus(int id) throws SQLException, ClassNotFoundException {
        List<Users> usersList = listOfUsers();

        for (Users u : usersList) {
            if (u.isSuspended()) {
                return true;
            }
        }

        return false;
    }


    public boolean loanBook(int isbn, int userId) throws SQLException, ClassNotFoundException {
        try (Connection conn = getConnection();
             PreparedStatement ps1 = conn.prepareStatement("INSERT INTO Loans (isbn, userId, date) VALUES (?, ?, ?)");
             PreparedStatement ps2 = conn.prepareStatement("UPDATE BooksDB SET onLoan = onLoan + 1, available = available - 1 WHERE isbn = ?")) {
            ps1.setInt(1, isbn);
            ps1.setInt(2, userId);
            ps1.setDate(3, new java.sql.Date(System.currentTimeMillis())); // use current date
            ps1.executeUpdate();
            ps2.setInt(1, isbn);
            ps2.executeUpdate();
            return true;
        }
    }

    public Date getLoanDate(int id, int isbn) throws SQLException, ClassNotFoundException {
        try (Connection connection = getConnection()) {
            PreparedStatement ps1 = connection.prepareStatement("SELECT date FROM Loans WHERE userId = ? AND isbn = ?");
            ps1.setInt(1, id);
            ps1.setInt(2, isbn);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                return rs.getDate("date");
            }
        }
        return null;
    }
    public boolean isLoanOverdue(Date loanDate) {
        long diffInMilliseconds = System.currentTimeMillis() - loanDate.getTime();
        long diffInDays = TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);

        return diffInDays > 15;
    }
    public int updateUserWarnings(int id) throws SQLException, ClassNotFoundException {
        try (Connection connection = getConnection()) {
            PreparedStatement ps3 = connection.prepareStatement("UPDATE UserDB SET warnings = warnings + 1 WHERE id = ?");
            ps3.setInt(1, id);
            return ps3.executeUpdate();
        }
    }
    public int getUserWarnings(int id) throws SQLException, ClassNotFoundException {
        try (Connection connection = getConnection()) {
            PreparedStatement ps4 = connection.prepareStatement("SELECT warnings FROM UserDB WHERE id = ?");
            ps4.setInt(1, id);
            ResultSet rs2 = ps4.executeQuery();

            if (rs2.next()) {
                return rs2.getInt("warnings");
            }
        }
        return 0;
    }
    public void resetUserWarnings(int id) throws SQLException, ClassNotFoundException {
        try (Connection connection = getConnection()) {
            PreparedStatement ps5 = connection.prepareStatement("UPDATE UserDB SET warnings = 0 WHERE id = ?");
            ps5.setInt(1, id);
            ps5.executeUpdate();
        }
    }



    public boolean returnItem(Users user, int isbn) throws ClassNotFoundException {

        int id = user.getId();


        try (Connection connection = getConnection()) {

            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM Loans WHERE userId = ? AND isbn = ?");
            ps2.setInt(1, id);
            ps2.setInt(2, isbn);
            int rowsDeleted = ps2.executeUpdate();

            if (rowsDeleted == 0) {
                return false;
            }

                PreparedStatement ps5 = connection.prepareStatement("UPDATE BooksDB SET onLoan = onLoan - 1, available = available + 1 WHERE isbn = ?");
                ps5.setInt(1, isbn);
                int rowsUpdated = ps5.executeUpdate();


            return true;
        } catch (SQLException ex) {
            System.out.println("An error occurred with the database while returning the item: " + ex.getMessage());
            return false;
        }
    }

    public int getAvailableBookAmount(int isbn) throws SQLException, ClassNotFoundException {
        int amount = 0;
        List<Books> booksList = listOfBooks();


        for (Books b : booksList) {
            if (b.getIsbn() == isbn) {
                amount = b.getAvailable();
            }
        }
        return amount;
    }

    public int getLoanQuantity(int id) throws ClassNotFoundException {

        //räknar mänden lån som ett id har i Loans. Detta kommer användas sedan för att kontrollera så att personer inte lånar mer än de får beroende på titelid.

        int amount = 0;

        try {

            Connection connection = getConnection();
            String query = "SELECT COUNT(*) FROM Loans WHERE userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return amount;
    }
    public int getSuspensionCount(int id) throws SQLException, ClassNotFoundException {

        Connection connection = getConnection();
        PreparedStatement checkPs = connection.prepareStatement("SELECT suspentionCount FROM UserDB WHERE id = ?");
        checkPs.setInt(1, id);
        ResultSet rs = checkPs.executeQuery();
        if (rs.next()) {
            return rs.getInt("suspentionCount");
        }
        return 0;
    }

    public boolean suspendUser(Users user, Date endDate) throws SQLException, ClassNotFoundException {
        int id = user.getId();

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE UserDB SET isSuspended = true, suspentionCount = suspentionCount + 1, suspentionStart = CURRENT_DATE, " +
                    "suspentionEnd = ? where id = ?");
            ps.setDate(1, endDate);
            ps.setInt(2, id);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                return false;
            }


        } catch (SQLException ex) {
            System.out.println("Error with DB" + ex.getMessage());


        }
        return true;
    }

    public boolean removeSuspention(Users user) throws ClassNotFoundException {

        int id = user.getId();

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE UserDB SET isSuspended = false, suspentionStart = NULL, suspentionEnd = NULL WHERE id = ?");
            ps.setInt(1, id);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                return false;
            }
            return true;

        } catch (SQLException ex) {
            System.out.println("Error with DB" + ex.getMessage());
            return false;
        }
    }

    public int getTitleId(int id) throws SQLException, ClassNotFoundException {

        List<Users> usersList = listOfUsers();

        for (Users u : usersList) {
            if (u.getId() == id) {
                return u.getTitleId();
            }
        }

        return 0;

    }

    public Books getBook(int isbn) throws SQLException, ClassNotFoundException {
        Books b = null;
        List<Books> booksList = listOfBooks();

        for (Books books : booksList) {
            if (books.getIsbn() == isbn) {
                b = new Books();
                b.setIsbn(books.getIsbn());
                b.setAvailable(books.getAvailable());
                b.setLoaned(books.getLoaned());
                b.setTitle(books.getTitle());
            }
        }


        return b;
    }

    public Loan getLoan(int id, int isbn) throws SQLException, ClassNotFoundException, LoanDoesNotExistException {
        Loan loan = null;
        List<Loan> loanList = listOfLoans();

        for (Loan l : loanList) {
            if (l.getIsbn() == isbn && l.getUserId() == id) {
                loan = new Loan();
                loan.setDate(l.getDate());
                loan.setLoanId(l.getLoanId());
                loan.setIsbn(l.getIsbn());
                loan.setUserId(l.getUserId());

            }
        }
        return loan;
    }

    public Users getUser(int id) throws SQLException, ClassNotFoundException {
        Users users = null;
        List<Users> usersList = listOfUsers();
        for (Users u : usersList) {
            if (u.getId() == id) {
                users = new Users();
                users.setId(u.getId());
                users.setfName(u.getfName());
                users.setlName(u.getlName());
                users.setsNum(u.getsNum());
                users.setSuspended(u.isSuspended());
                users.setWarnings(u.getWarnings());
            }
        }

        return users;
    }
    public Users getUsersNum(int sNum) throws SQLException, ClassNotFoundException {
        Users users = null;
        List<Users> usersList = listOfUsers();
        for (Users u : usersList) {
            if (u.getsNum() == sNum) {
                users = new Users();
                users.setId(u.getId());
                users.setfName(u.getfName());
                users.setlName(u.getlName());
                users.setsNum(u.getsNum());
                users.setSuspended(u.isSuspended());
                users.setWarnings(u.getWarnings());
                break;
            }
        }


        return users;
    }

    public void updateBookAmount(int id) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement loanPs = connection.prepareStatement("SELECT isbn FROM Loans WHERE userId = ?");
        loanPs.setInt(1, id);
        ResultSet loanRs = loanPs.executeQuery();
        while (loanRs.next()) {
            String isbn = loanRs.getString("isbn");
            PreparedStatement bookPs = connection.prepareStatement("UPDATE BooksDB SET onLoan = onLoan - 1, available = available + 1 WHERE isbn = ?");
            bookPs.setString(1, isbn);
            bookPs.executeUpdate();
        }
    }
}
