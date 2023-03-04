import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {


        //test av databas metod. sout funkar inte eftersom det är av objekt typ, men det är ingen ide att fixa i
        //databas metoden eftersom den endast ska hämta datan. Sedan kan get användas för att få ut
        //läsbardata
        DatabaseMethods m = new DatabaseMethods();

        List l = m.listOfUsers();

        System.out.println(l);


        /////

        //Users user = new Users(3334,"Erik", "Eriksson", 3, 990725); funkar!

        //m.addNewUser(user); funkar!!

        int z = m.getLoanQuantity(3334);//funkar
        System.out.println(z);//funkar


    }
}
