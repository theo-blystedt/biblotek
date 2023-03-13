
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Main {
    private static final Logger logger = (Logger) LogManager.getLogger(Database.class);
    public static void main(String[] args) throws SQLException, ClassNotFoundException, UserHasActiveLoansException, UserAlreadyExistExeption, UserDoesNotExistException, LoanDoesNotExistException {


        Database dm = new Database();
        LibrarieService ls = new LibrarieService(dm);
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.print("Välkommen till vårat bibliotek! Skriv in din id för att logga in! :");
        int userId = sc.nextInt();

        int titleId = dm.getTitleId(userId);


        while (running) {
            if (titleId == 1) {
                // Meny för admin
                System.out.println("Admin meny:");
                System.out.println("1. Lägg till användare"); //funkar
                System.out.println("2. Ta bort användare"); //funkar
                System.out.println("3. Stäng av användare till bestämt datum"); //funkar. Både med vanlig suspention och att användaren tas bort vid 2 avstängningar
                System.out.println("4: Se lista på alla användare"); //funkar
                System.out.println("5: Låna bok"); //funkar
                System.out.println("6: Returnera bok");//funkar. Har inte kollat med suspend och varningar när lån är försenat
                System.out.println("7. Ta bort avstängning från användare");//funkar
                System.out.println("8. Avsluta");
                System.out.print("Välj ett alternativ: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:

                        System.out.println("Skriv in titleId för anändare; 1 = admin, 2 = Undergrad, 3 = Postgrad, 4 = PHD, 5 = Lärare");
                        int titleId1 = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Skrive in förnamn");
                        String fname = sc.nextLine();


                        System.out.println("Skriv in efternamn");
                        String lName = sc.nextLine();


                        System.out.println("Skriv in personnummer på personen : ex 990613");
                        int sNum = sc.nextInt();

                        try {
                            ls.addUser(fname, lName, titleId1, sNum);
                            System.out.println("Användare tillagd!");
                            logger.info("användare: " + userId + "la till en användare");
                        }catch (UserAlreadyExistExeption uax){
                            System.out.println("Användare finns redan i systemet");
                        }
                        break;

                    case 2:
                        try {
                            System.out.println("Skriv in id på personen som ska tas bort: ");
                            int tabortId = sc.nextInt();
                            ls.deleteUser(tabortId);
                            System.out.println("Användare raderad!");
                            logger.info("användare: " + userId + "tog bort en användare");

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
                            sc.nextLine();
                            String date = sc.nextLine();
                            try {
                                Date dateEnd = dateFormat.parse(date);
                                java.sql.Date sqlDateEnd = new java.sql.Date(dateEnd.getTime());
                                ls.suspendUser(suspendId, sqlDateEnd);
                                System.out.println("Användare avstängd!");
                                logger.info("användare: " + userId + "stängde av en användare id: " + suspendId);
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
                        ls.ListOfUsers();
                        logger.info("användare: " + userId + "hämtade lista av alla användare");
                        break;
                    case 5:
                        System.out.println("Skriv in id på användaren som ska låna boken: ");
                        int loanUId = sc.nextInt();
                        System.out.println("Skriv isbn på boken som ska lånas (000000): ");
                        int isbn = sc.nextInt();
                        try{
                            ls.loan(isbn,loanUId);
                            System.out.println("Lånad!");
                            logger.info("användare: " + userId + "lånade en bok till" + loanUId);

                        }catch (UserHasNoMoreLoansException ux){
                            System.out.println("Användare kan inte låna fler böcker");
                        } catch (NotEnoughBooksInStoreException nx){
                            System.out.println("Ingen bok tillgänglig för att låna");
                        }catch (UserIsSuspendedException sx){
                            System.out.println("User is currently suspended");
                        }catch (LoanDoesNotExistException eeee){
                            System.out.println("Loan does not exist");
                        }
                        break;
                    case 6:
                        System.out.println("Skriv in id på användare som ska returnera boken: ");
                        int loanid = sc.nextInt();
                        System.out.println("Skriv in isbn på boken som ska returneras");
                        int loanisbn = sc.nextInt();
                        try{
                            ls.returnItem(loanid,loanisbn);
                            System.out.println("Returnerad!\n");
                            logger.info("användare: " + userId + "returnerade en bok");
                        } catch (UserDoesNotExistException ux2) {
                            System.out.println("Användare eller bok finns inte i systemet. Användare kan ha automatiskt blivit borttagen pga försening");
                        }catch (ClassNotFoundException classNotFoundException){
                            System.out.println("Fel med klasser");
                        }
                        break;
                    case 7:
                        System.out.println("Skriv in id på användare som ska låsas upp: ");
                        int suspendLiftId = sc.nextInt();
                        try{
                            ls.removeSuspention(suspendLiftId);
                            System.out.println("Användare upplåst!");
                            logger.info("användare: " + userId + "låste upp en användare");
                        } catch (UserDoesNotExistException userDoesNotExistException){
                            System.out.println("Användare finns inte i systemet");
                        } catch (ClassNotFoundException classNotFoundException){
                            System.out.println("Fel med klasser");
                        } catch (NullPointerException ex){
                            System.out.println("Användare finns inte");
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
        logger.info("användare: " + userId + "loggade ut");
    }
}
