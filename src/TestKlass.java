import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestKlass {

    @Mock
    private Database dm;

    private LibrarieService librarieService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        librarieService = new LibrarieService(dm);
    }

    @Test
    public void testDeleteNonExistingUser() throws SQLException, ClassNotFoundException, UserDoesNotExistException {

        when(dm.getUser(123)).thenReturn(null);


        assertThrows(UserDoesNotExistException.class, () -> {
            librarieService.deleteUser(123);
        });

        verify(dm, never()).deleteUser(any(Users.class));
    }

    @Test
    public void testDeleteUser() throws SQLException, UserHasActiveLoansException, UserDoesNotExistException, ClassNotFoundException {

        Users user = new Users(1, "Theo", "Blystedt", 1, 123455, false, 0);

        when(dm.getUser(1)).thenReturn(user);

        Users deletedUser = librarieService.deleteUser(1);

        verify(dm).deleteUser(user);

        assertEquals(user, deletedUser);
    }

    @Test
    public void addUser1Mockito() throws SQLException, ClassNotFoundException, UserAlreadyExistExeption, UserDoesNotExistException {
        Database dm = mock(Database.class);
        LibrarieService test = new LibrarieService(dm);
        ArrayList<Users> users = new ArrayList<>();
        users.add(new Users("Albin", "Sandberg", 3, 990512));
        when(dm.listOfUsers()).thenReturn(users);

        assertNotNull(test.addUser("Albin", "Sandberg", 3, 990512));
    }

    @Test
    public void addUser2Mockito() throws SQLException, ClassNotFoundException, UserDoesNotExistException, UserAlreadyExistExeption {
        Database dm = mock(Database.class);

        ArrayList<Users> users = new ArrayList<>();
        users.add(new Users("Hej", "Test", 3, 889922));
        when(dm.listOfUsers()).thenReturn(users);

        assertNull(librarieService.addUser("Hej", "Test", 4, 882299));
    }

    @Test
    public void testAddUserThrowsUserAlreadyExistException() throws SQLException, ClassNotFoundException, UserDoesNotExistException {

        String fName = "Martin";
        String lName = "Marsilius";
        int titleId = 1;
        int sNum = 1234;


        Users existingUser = new Users("Hej", "Test", 2, sNum);
        List<Users> users = new ArrayList<>();
        users.add(existingUser);

        when(dm.listOfUsers()).thenReturn(users);
        when(dm.getUsersNum(sNum)).thenReturn(existingUser);

        Users testUser = new Users(fName, lName, titleId, sNum);

        assertThrows(UserAlreadyExistExeption.class, () -> {
            Users result = librarieService.addUser(fName, lName, titleId, sNum);
        });
    }

    @Test
    public void suspendUserTest1() throws SQLException, ClassNotFoundException, UserDoesNotExistException {

        Users testUser = new Users();
        testUser.setId(1);
        testUser.setfName("hej");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 15);
        Date testEndDate = new java.sql.Date(calendar.getTimeInMillis());

        when(dm.getUser(testUser.getId())).thenReturn(testUser);

        when(dm.suspendUser(testUser, testEndDate)).thenReturn(true);

        Users suspendedUser = librarieService.suspendUser(testUser.getId(), testEndDate);

        verify(dm).getUser(testUser.getId());

        verify(dm).suspendUser(testUser, testEndDate);

        assertEquals(testUser, suspendedUser);
    }

    @Test
    public void suspendUserUserDoesNotExistException() throws SQLException, UserDoesNotExistException, ClassNotFoundException {

        int userId = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 15);
        Date endDate = new java.sql.Date(calendar.getTimeInMillis());

        assertThrows(UserDoesNotExistException.class, () -> {
            librarieService.suspendUser(userId, endDate);
        });
    }

    @Test
    public void testReturnItem() throws SQLException, LoanDoesNotExistException, ClassNotFoundException, UserDoesNotExistException {

        int userId = 1;
        int isbn = 12345;
        Users user = mock(Users.class);
        when(dm.getUser(userId)).thenReturn(user);
        when(dm.returnItem(user, isbn)).thenReturn(true);
        Loan loan = mock(Loan.class);
        when(dm.getLoan(userId, isbn)).thenReturn(loan);

        Loan result = librarieService.returnItem(userId, isbn);

        assertEquals(loan, result);
    }


    @Test
    public void testReturnItemUserDoesNotExist() throws SQLException, LoanDoesNotExistException, ClassNotFoundException {

        int userId = 1;
        int isbn = 12345;
        Users user = mock(Users.class);
        when(dm.getUser(userId)).thenReturn(user);
        when(dm.returnItem(user, isbn)).thenReturn(false);


        assertThrows(UserDoesNotExistException.class, () -> {
            librarieService.returnItem(userId, isbn);
        });
    }

    @Test
    public void testRemoveSuspention() throws SQLException, ClassNotFoundException, UserDoesNotExistException {
        int userId = 1;
        Users user = mock(Users.class);
        when(dm.getUser(userId)).thenReturn(user);
        when(dm.removeSuspention(user)).thenReturn(true);


        Users result = librarieService.removeSuspention(userId);

        assertEquals(user, result);
    }

    @Test
    public void testRemoveSuspentionUserDoesNotExist() throws SQLException, ClassNotFoundException {

        int userId = 1;
        Users user = mock(Users.class);
        when(dm.getUser(userId)).thenReturn(user);
        when(dm.removeSuspention(user)).thenReturn(false);


        assertThrows(UserDoesNotExistException.class, () -> {
            librarieService.removeSuspention(userId);
        });

    }

    @Test
    public void testLoan() throws Exception {

        when(dm.userLoanLimit(1)).thenReturn(5);
        when(dm.getLoanQuantity(1)).thenReturn(2);
        when(dm.getAvailableBookAmount(12)).thenReturn(2);
        when(dm.isSuspendedStatus(1)).thenReturn(false);
        Calendar calendar = Calendar.getInstance();
        Date endDate = new java.sql.Date(calendar.getTimeInMillis());
        Loan loan = new Loan(12, 1, endDate,22);
        when(dm.getLoan(1, 12)).thenReturn(loan);


        Loan result = librarieService.loan(12, 1);

        assertEquals(loan, result);
    }
    @Test
    public void testLoanBookUserHasNoMoreLoansException() throws SQLException, ClassNotFoundException, UserHasNoMoreLoansException, NotEnoughBooksInStoreException, UserIsSuspendedException {

        int isbn = 123;
        int userId = 456;
        doReturn(2).when(dm).userLoanLimit(userId);
        doReturn(2).when(dm).getLoanQuantity(userId);

        assertThrows(UserHasNoMoreLoansException.class, () -> librarieService.loan(isbn, userId));
    }

    @Test
    public void testLoanBookUserIsSuspendedException() throws SQLException, ClassNotFoundException, UserHasNoMoreLoansException, NotEnoughBooksInStoreException, UserIsSuspendedException {
        int isbn = 123;
        int userId = 456;
        doReturn(2).when(dm).userLoanLimit(userId);
        doReturn(1).when(dm).getLoanQuantity(userId);
        doReturn(1).when(dm).getAvailableBookAmount(isbn);
        doReturn(true).when(dm).isSuspendedStatus(userId);


        assertThrows(UserIsSuspendedException.class, () -> librarieService.loan(isbn, userId));
    }
    @Test
    public void testLoanBookNotEnoughBooksInStoreException() throws SQLException, ClassNotFoundException, UserHasNoMoreLoansException, NotEnoughBooksInStoreException, UserIsSuspendedException {

        int isbn = 123;
        int userId = 456;
        doReturn(2).when(dm).userLoanLimit(userId);
        doReturn(1).when(dm).getLoanQuantity(userId);
        doReturn(0).when(dm).getAvailableBookAmount(isbn);

        assertThrows(NotEnoughBooksInStoreException.class, () -> librarieService.loan(isbn, userId));
    }







}




