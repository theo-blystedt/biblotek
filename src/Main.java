import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {


        //test av databas metod. sout funkar inte eftersom det är av objekt typ, men det är ingen ide att fixa i
        //databas metoden eftersom den endast ska hämta datan
        DatabaseMethods m = new DatabaseMethods();

        List l = m.listOfUsers();

        System.out.println();


    }
}
