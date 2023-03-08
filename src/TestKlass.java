import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.SQLException;

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
    public void testDeleteUser() throws SQLException, UserHasActiveLoansException, UserDoesNotExistException, ClassNotFoundException {
        // create a user object
        Users user = new Users(1, "John", "Doe", 1, 1234, false, 0);

        // mock the getUser method to return the user object
        when(dm.getUser(1)).thenReturn(user);

        // call the deleteUser method
        Users deletedUser = librarieService.deleteUser(1);

        // verify that the deleteUser method was called with the correct user object
        verify(dm).deleteUser(user);

        // verify that the deletedUser object is the same as the original user object
        assertEquals(user, deletedUser);
    }

}
