import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LibrarieService {

    Database dm;

    public LibrarieService(Database dm){
        this.dm = dm;
    }

	public Users addUser(String fName, String lName, int titleId, int sNum) throws SQLException, ClassNotFoundException, UserAlreadyExistExeption {
		Users newUser = new Users(fName,lName,titleId,sNum);

		dm.addNewUser(newUser);

		Users test = null;

		try{
			List<Users> ex = dm.listOfUsers();

			for(Users u: ex){
				if(fName.equals(u.getfName()) && lName.equals(u.getlName()) && titleId == u.getTitleId() && sNum == u.getsNum()){
					test = new Users();
					test.setfName(u.getfName());
					test.setlName(u.getlName());
					test.setTitleId(u.getTitleId());
					test.setsNum(u.getsNum());
				}
			}

		}
		catch(NullPointerException ex){
			System.out.println("Fel i listan" + ex);
		}

		return test;
	}



	public boolean loan(int isbn, int memberId) throws UserDoesNotExistException, SQLException, ClassNotFoundException {
		boolean status = false;

		Users memberInfo = dm.getUser(memberId);
		Books book = dm.getBook(isbn);

		// more code here...

		return status;
	}
}
