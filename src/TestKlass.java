import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
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
    public void testDeleteUser() throws SQLException, UserHasActiveLoansException, UserDoesNotExistException, ClassNotFoundException, UserAlreadyExistExeption {

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
    public void addUser2Mockito() throws SQLException, ClassNotFoundException, UserAlreadyExistExeption, UserDoesNotExistException {
        Database dm = mock(Database.class);
        LibrarieService test = new LibrarieService(dm);
        ArrayList<Users> users = new ArrayList<>();
        users.add(new Users("Hej", "Test", 3, 889922));
        when(dm.listOfUsers()).thenReturn(users);

        assertNull(test.addUser("Hej", "Test", 4, 882299));
    }

    @Test
    public void testAddUserThrowsUserAlreadyExistException() throws SQLException, ClassNotFoundException, UserAlreadyExistExeption, UserDoesNotExistException {

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

}
