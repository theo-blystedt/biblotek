import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //bara massa oläsbart skit när jag testar metoder och liknande. Men här ska sedan menyn in
        DatabaseMethods m = new DatabaseMethods();

        List<Users> l = m.listOfUsers();



        for(Users u : l){
            System.out.println("Name: " + u.getfName() + " " + u.getlName() + "(id: " + u.getId()
                    + ", titldId: " + u.getTitleId() + ", Social number: " + u.getsNum() + ")");
        }


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
