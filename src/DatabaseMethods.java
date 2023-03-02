import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DatabaseMethods {

    public static Connection getConnection() throws SQLException, ClassNotFoundException{
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

        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String fName = resultSet.getString("fName");
            String lName = resultSet.getString("lName");
            int titleId = resultSet.getInt("titleId");
            int sNum = resultSet.getInt("sNum");

            Users user = new Users(id,fName,lName,titleId,sNum);
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

        while (resultSet.next()){
            int isbn = resultSet.getInt("isbn");
            String title = resultSet.getString("title");
            int available = resultSet.getInt("available");
            int onLoan = resultSet.getInt("onLoan");

            Books books = new Books(title,isbn,available,onLoan);
            listOfBooks.add(books);
        }
        resultSet.close();
        statement.close();
        connection.close();

        return listOfBooks;

    }

    public boolean addNewUser(Users user) throws SQLException, ClassNotFoundException {
        boolean ok = true;

        Connection connection = getConnection();
        String query = "INSERT INTO UserDB (id,fName,lName,titleId,sNum) values (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,user.getId());
        preparedStatement.setString(2,user.getfName());
        preparedStatement.setString(3, user.getlName());
        preparedStatement.setInt(4,user.getTitleId());
        preparedStatement.setInt(5,user.getsNum());
        int rowsInserted = preparedStatement.executeUpdate();

        //kanske kan skippa id eftersom att id är autoincrement i databasen

        if(rowsInserted <= 0){
            ok = false;
        }

        preparedStatement.close();
        connection.close();


        return ok;
    }

    public boolean deleteUser(int id){
        boolean ok = true;

        //tar bort användare från databasen

        return ok;
    }

    //public Users userStatus(int id){ //kolla status på användare typ roll, varningar, lån osv.

       // Users x = new Users();

       // return x;
    //}


}
