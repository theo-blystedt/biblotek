import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DatabaseMethods {

    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/194.47.178.142";
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

    public boolean addNewUser(){
        boolean ok = true;

        //lägger till i database, returnerar true om person blev tillagd

        return ok;
    }

    public boolean deleteUser(){
        boolean ok = true;

        //tar bort användare från databasen

        return ok;
    }

    //public Users userStatus(int id){ //kolla status på användare typ roll, varningar, lån osv.

       // Users x = new Users();

       // return x;
    //}


}
