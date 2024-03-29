import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class LibrarieService {

    Database dm;

    public LibrarieService(Database dm) {
        this.dm = dm;
    }

    public LibrarieService() {

    }


    public Users addUser(String fName, String lName, int titleId, int sNum) throws SQLException, ClassNotFoundException, UserAlreadyExistExeption, UserDoesNotExistException {
        Users newUser = new Users(fName, lName, titleId, sNum);

        List<Users> users = dm.listOfUsers();

        Users user = dm.getUsersNum(sNum);

        if (user != null) {
            throw new UserAlreadyExistExeption();
        }

        dm.addNewUser(newUser);

        Users test = null;

        try {
            List<Users> ex = dm.listOfUsers();

            for (Users u : ex) {
                if (fName.equals(u.getfName()) && lName.equals(u.getlName()) && titleId == u.getTitleId() && sNum == u.getsNum()) {
                    test = new Users();
                    test.setfName(u.getfName());
                    test.setlName(u.getlName());
                    test.setTitleId(u.getTitleId());
                    test.setsNum(u.getsNum());
                }
            }

        } catch (SQLException sqlException) {
            System.out.println("fel i databas");
        }

        return test;
    }


    public Users deleteUser(int id) throws SQLException, UserDoesNotExistException, ClassNotFoundException {
        Users ex = dm.getUser(id);


        if (ex == null) {
            throw new UserDoesNotExistException();
        }

        dm.deleteUser(ex);

        return ex;
    }

    public Users suspendUser(int id, Date endDate) throws SQLException, UserDoesNotExistException, ClassNotFoundException {
        Users ex = dm.getUser(id);

        int suspensionCount = dm.getSuspensionCount(id);

        if (suspensionCount >= 1) {
            dm.deleteUser(ex);
        } else {
            boolean success = dm.suspendUser(ex, endDate);
            if (!success) {
                throw new UserDoesNotExistException();
            }
        }

        return ex;
    }

    public Loan returnItem(int id, int isbn) throws SQLException, LoanDoesNotExistException, ClassNotFoundException, UserDoesNotExistException {

        Users user = dm.getUser(id);
        Loan loan = dm.getLoan(id, isbn);

        Date date = dm.getLoanDate(id, isbn);

        if (dm.isLoanOverdue(date)) {
            dm.updateUserWarnings(id);
            if (dm.getUserWarnings(id) >= 2) {
                dm.resetUserWarnings(id);
                suspendUser(id, new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)));
            }
        }
        boolean succes = dm.returnItem(user, isbn);
        if (!succes) {
            throw new UserDoesNotExistException(); //or book
        }
        dm.returnItem(user,isbn);

        return loan;
    }

    public Users removeSuspention(int id) throws SQLException, ClassNotFoundException, UserDoesNotExistException {
        Users user = dm.getUser(id);

        boolean success = dm.removeSuspention(user);

        if (!success) {
            throw new UserDoesNotExistException();
        }

        dm.removeSuspention(user);

        return user;

    }


    public Loan loan(int isbn, int memberId) throws UserDoesNotExistException, SQLException, ClassNotFoundException, LoanDoesNotExistException, NotEnoughBooksInStoreException, UserIsSuspendedException, UserHasNoMoreLoansException {

        int userLimit = dm.userLoanLimit(memberId);
        int userLoanQuantity = dm.getLoanQuantity(memberId);
        int availableBooks = dm.getAvailableBookAmount(isbn);
        boolean isSuspended = dm.isSuspendedStatus(memberId);

        if (userLoanQuantity >= userLimit) {
            throw new UserHasNoMoreLoansException();
        } else if (availableBooks <= 0) {
            throw new NotEnoughBooksInStoreException();
        } else if (isSuspended) {
            throw new UserIsSuspendedException();
        } else {
            try {
                dm.loanBook(isbn, memberId);
                return dm.getLoan(memberId, isbn);
            } catch (SQLException | ClassNotFoundException ex) {
                throw ex;
            }
        }
    }


    public void ListOfUsers() throws SQLException, ClassNotFoundException {
        for (Users u : dm.listOfUsers()) {
            System.out.println("Namn: " + u.getfName() + " " + u.getlName() + " (ID: " + u.getId() + ", TitleId: " + u.getTitleId()
                    + ", Social number: " + u.getsNum() + ")");
        }
    }
}
