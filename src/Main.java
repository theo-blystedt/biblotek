import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {


        DatabaseMethods dm = new DatabaseMethods();
        List<Users> usersList = dm.listOfUsers();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.print("Välkommen till vårat bibliotek! Skriv in din id för att logga in! :");
        int userId = sc.nextInt();

        int titleId = dm.getTitleId(userId);


        while (running) {
            if (titleId == 1) {
                // Meny för admin
                System.out.println("Admin meny:");
                System.out.println("1. Lägg till användare"); //funkar inte än
                System.out.println("2. Ta bort användare"); //funkar
                System.out.println("3. Stäng av användare till bestämt datum"); //funkar inte än. fel med datum format
                System.out.println("4: Se lista på alla användare"); //funkar
                System.out.println("5: Låna bok"); //funkar
                System.out.println("6: Returnera bok");//funkar. Har inte kollat med suspend och varningar när lån är försenat
                System.out.println("7. Ta bort avstängning av användare");//funkar
                System.out.println("8. Avsluta");
                System.out.print("Välj ett alternativ: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        //vill inte sätta fname och lname in i databasen. Så de är tom. Testat med
                        //direkt konstruktor och set. Därför är det något problem med databas metoden.
                        System.out.println("Skrive in förnamn");
                        String fname = sc.nextLine();

                        sc.next();
                        System.out.println("Skriv in efternamn");
                        String lName = sc.nextLine();

                        sc.next();
                        System.out.println("Skriv in titleId för anändare; 1 = admin, 2 = Undergrad, 3 = Postgrad, 4 = PHD, 5 = Lärare");
                        int titleId1 = sc.nextInt();

                        System.out.println("Skriv in personnummer på personen : ex 990613");
                        int sNum = sc.nextInt();
                        Users user = new Users(fname,lName,titleId1,sNum);

                        if(dm.addNewUser(user)){
                            System.out.println("Användare tillagd!");
                            break;
                        }
                        else {
                            System.out.println("Personen med person nummer " + user.getsNum() + " finns redan");
                            break;
                        }
                    case 2:
                        try {
                            System.out.println("Skriv in id på personen som ska tas bort: ");
                            int tabortId = sc.nextInt();
                            boolean deleted = dm.deleteUser(tabortId);
                            if (deleted) {
                                System.out.println("Användare bortagen");
                            } else {
                                System.out.println("Kunde inte ta bort användaren");
                            }
                        } catch (UserDoesNotExistException e) {
                            System.out.println("Användaren finns inte i databasen");
                        } catch (SQLException e) {
                            System.out.println("Error deleting user from database: " + e.getMessage());
                        } catch (ClassNotFoundException e) {
                            System.out.println("Class not found: " + e.getMessage());
                        }
                        break;
                    case 3:
                        try{
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            System.out.println("Skriv in id på användare som ska få avstängning: ");
                            int suspendId = sc.nextInt();

                            System.out.println("Skriv in slutdatum på avstängling (DD/MM/YYYY: ");
                            sc.next();
                            String date = sc.nextLine();
                            try {
                                Date dateEnd = (Date) dateFormat.parse(date);
                                dm.suspendUser(suspendId, dateEnd); //kanske inte funkar
                                System.out.println("Användare avstängd!");
                            } catch (ParseException e) {
                                System.out.println("Fel datum format, dd/MM/yyyy");
                            } catch (UserDoesNotExistException ez){
                                System.out.println("Användare med id: " + suspendId + "Finns inte");
                            } catch (SQLException se){
                                System.out.println("Fel med databasen");
                            }
                            break;
                        } catch (ClassNotFoundException e) {
                            System.out.println("Class problem");
                        }
                    case 4:
                        for(Users u : usersList){
                            System.out.println("Namn: " + u.getfName() + " " + u.getlName() + " (ID: " + u.getId() + ", TitleId: " + u.getTitleId()
                            + ", Social number: " + u.getsNum() + ")");
                        }
                        break;
                    case 5:
                        System.out.println("Skriv in id på användaren som ska låna boken: ");
                        int loanUId = sc.nextInt();
                        System.out.println("Skriv isbn på boken som ska lånas (000000): ");
                        int isbn = sc.nextInt();
                        try{
                            dm.loanBook(isbn,loanUId);
                            System.out.println("Lånad!\n");

                        }catch (UserHasNoMoreLoansException ux){
                            System.out.println("Användare kan inte låna fler böcker");
                        } catch (NotEnoughBooksInStoreException nx){
                            System.out.println("Ingen bok tillgänglig för att låna");
                        }catch (UserIsSuspendedException sx){
                            System.out.println("User is currently suspended");
                        }
                        break;
                    case 6:
                        System.out.println("Skriv in id på användare som ska returnera boken: ");
                        int loanid = sc.nextInt();
                        System.out.println("Skriv in isbn på boken som ska returneras");
                        int loanisbn = sc.nextInt();
                        try{
                            dm.returnItem(loanid,loanisbn);
                            System.out.println("Returnerad!\n");
                        } catch (UserDoesNotExistException ux2) {
                            System.out.println("Användare eller bok finns inte i systemet");
                        }catch (ClassNotFoundException classNotFoundException){
                            System.out.println("Fel med klasser");
                        }
                        break;
                    case 7:
                        System.out.println("Skriv in id på användare som ska låsas upp: ");
                        int suspendLiftId = sc.nextInt();
                        try{
                            dm.removeSuspention(suspendLiftId);
                            System.out.println("Användare upplåst!");
                        } catch (UserDoesNotExistException userDoesNotExistException){
                            System.out.println("Användare finns inte i systemet");
                        } catch (ClassNotFoundException classNotFoundException){
                            System.out.println("Fel med klasser");
                        }
                        break;
                    case 8:
                        running = false;
                        break;
                    default:
                        System.out.println("Ogiltigt val");
                        break;
                }
            } else {
                System.out.println("Vi hitta ingen användare med detta id. Vänligen starta om programmet och testa igen");
                running = false;
            }
        }

        System.out.println("Tack för besöket!");
    }
}
