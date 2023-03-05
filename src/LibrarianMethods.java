import java.util.ArrayList;
import java.util.List;

public class LibrarianMethods {

    DatabaseMethods dm = new DatabaseMethods();

    public void printUserList(){
        List<Users> usersList = new ArrayList<>();

        for(Users u : usersList){
            System.out.println("Name: " + u.getfName() + " " + u.getlName() + "(id: " + u.getId()
            + ", titldId: " + u.getTitleId() + ", Social number: " + u.getsNum() + ")");
        }
    }


}
