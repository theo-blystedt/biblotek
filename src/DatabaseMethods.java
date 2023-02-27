import java.util.ArrayList;
import java.util.List;

public class DatabaseMethods {

    public List<Users> listOfUsers(){

        List<Users> listOfUsers = new ArrayList<>();

        //Hämta alla användare från databasen och lägg de i listan.

        return listOfUsers;

    }

    public List<Books> listOfBooks(){

        List<Books> listOfBooks = new ArrayList<>();

        //hämta alla böcker och lägg i listan

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


}
