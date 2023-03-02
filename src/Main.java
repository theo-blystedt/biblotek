import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        DatabaseMethods m = new DatabaseMethods();

        List l = m.listOfUsers();

        System.out.println();


    }
}
