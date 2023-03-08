import java.sql.Date;
import java.sql.SQLException;
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


	public Users deleteUser(int id) throws SQLException, UserHasActiveLoansException, UserDoesNotExistException, ClassNotFoundException {
		Users ex = dm.getUser(id);

			dm.deleteUser(ex);

		return ex;
	}

	public Users suspendUser(int id, Date endDate) throws SQLException, UserDoesNotExistException, ClassNotFoundException, UserHasActiveLoansException {
		Users ex = dm.getUser(id);

		dm.suspendUser(ex,endDate);


		return ex;

	}

	public Loan returnItem(int id, int isbn) throws SQLException, LoanDoesNotExistException, ClassNotFoundException, UserDoesNotExistException, UserHasActiveLoansException {
		Users user = dm.getUser(id);
		Loan loan = dm.getLoan(id,isbn);
		dm.returnItem(user,isbn);

		return loan;
	}

	public Users removeSuspention(int id) throws SQLException, UserDoesNotExistException, ClassNotFoundException {
		Users user = dm.getUser(id);

		dm.removeSuspention(user);

		return user;

	}



	public Loan loan(int isbn, int memberId) throws UserDoesNotExistException, SQLException, ClassNotFoundException, LoanDoesNotExistException, NotEnoughBooksInStoreException, UserIsSuspendedException, UserHasNoMoreLoansException {
		boolean status = false;
		Loan loan = dm.getLoan(memberId,isbn);

		dm.loanBook(isbn,memberId);

		return loan;
	}
}