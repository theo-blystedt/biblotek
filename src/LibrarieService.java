import java.sql.SQLException;

//test klass just nu efter ny info om klasser från Lars

public class LibrarieService {

    DatabaseMethods dm;

    public LibrarieService(DatabaseMethods dm){
        this.dm = dm;
    }



	public boolean borrow(int isbn, int memberId) throws UserDoesNotExistException, SQLException, ClassNotFoundException {
		boolean status = false;

		Users memberInfo = dm.getUser(memberId);
		Books book = dm.getBook(isbn);

		// more code here...

		return status;
	}
}
