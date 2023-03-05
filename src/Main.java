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


        //funkar eftersom att erik redan är i databased, funkar även att adda han
        Users user = new Users(3334,"Erik", "Eriksson", 3, 990725);

        if(m.addNewUser(user)){
            System.out.println("User added");
        }
        else {
            System.out.println("User cannot be added");
        }

        //funkar

        /*Users testdeluser = new Users(9999,"jag tas", "bort",3,667788);
        if(m.addNewUser(testdeluser)){
            System.out.println("Delete user added");
        }
        else {
            System.out.println("something went wrong");
        }



        if(m.deleteUser(9999)){
            System.out.println("I was deleted");
        }
        else {
            System.out.println("I was not deleted");
        }

         */


        /*funkar!
        int z = m.getLoanQuantity(3334);//funkar
        System.out.println(z);//funkar

        if(m.loanBook(445566,4567)){
            System.out.println("Book was loaned");
        }
        else{
            System.out.println("Cant loan book");
        }

         */


    }
}
