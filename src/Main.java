import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        DatabaseMethods dm = new DatabaseMethods();
        List<Users> usersList = dm.listOfUsers();
        /*List<Books> booksList = dm.listOfBooks();
        List<Loan> loanList = dm.listOfLoans();*/ // kanske inte behöver.
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        int userId = sc.nextInt();

        System.out.print("Välkommen till vårat bibliotek! Skriv in din id för att logga in! :");

        if(dm.getTitleId(userId) == 0){
            System.out.println("Vi hittar ingen användare med detta id. Vänligen starta om programmet och testa igen");
        }





    }
}
