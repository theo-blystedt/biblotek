import java.sql.SQLException;
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
                System.out.println("1. Lägg till användare");
                System.out.println("2. Ta bort användare");
                System.out.println("3. Stäng av användare till bestämt datum");
                System.out.println("4: Se lista på alla användare");
                System.out.println("5: Låna bok");
                System.out.println("6: Returnera bok");
                System.out.println("7. Ta bort avstängning av användare");
                System.out.println("8. Avsluta");
                System.out.print("Välj ett alternativ: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        Users user = new Users();
                        System.out.println("Skrive in förnamn");
                        user.setfName(sc.nextLine());
                        System.out.println("Skriv in efternamn");
                        user.setlName(sc.nextLine());
                        System.out.println("Skriv in titleId för anändare; 1 = admin, 2 = Undergrad, 3 = Postgrad, 4 = PHD, 5 = Lärare");
                        user.setTitleId(sc.nextInt());
                        System.out.println("Skriv in personnummer på personen : ex 990613");
                        user.setsNum(sc.nextInt());

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

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

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
