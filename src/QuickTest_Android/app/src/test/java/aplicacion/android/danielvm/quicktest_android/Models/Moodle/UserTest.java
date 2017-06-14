package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Clase UserTest encargada de realizar las pruebas unitaria sobre la clase User.
 *
 * @author Daniel Puente Gabarri.
 */

public class UserTest {

    @Test
    public void userTest() {

        int id = 1;
        String username = "name";
        String firstname = "namememe";
        String lastname = "lasnamenamename";
        String fullname = "fulll";
        String email = "dpg222@aa.es";

        User user = new User(id, username, firstname, lastname, fullname, email);

        // Comprobamos el contenido
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(firstname, user.getFirstname());
        assertEquals(lastname, user.getLastname());
        assertEquals(fullname, user.getFullname());
        assertEquals(email, user.getEmail());

        // Modificamos el contenido

        int id2 = 11;
        String username2 = "name2";
        String firstname2 = "namememe222";
        String lastname2 = "lasnamenamename222";
        String fullname2 = "fulll222";
        String email2 = "dpg222@aa.es2222";

        user.setId(id2);
        user.setUsername(username2);
        user.setFirstname(firstname2);
        user.setLastname(lastname2);
        user.setFullname(fullname2);
        user.setEmail(email2);

        // Comprobamos el nuevo contenido

        assertNotEquals(id, user.getId());
        assertNotEquals(username, user.getUsername());
        assertNotEquals(firstname, user.getFirstname());
        assertNotEquals(lastname, user.getLastname());
        assertNotEquals(fullname, user.getFullname());
        assertNotEquals(email, user.getEmail());

        assertEquals(id2, user.getId());
        assertEquals(username2, user.getUsername());
        assertEquals(firstname2, user.getFirstname());
        assertEquals(lastname2, user.getLastname());
        assertEquals(fullname2, user.getFullname());
        assertEquals(email2, user.getEmail());
    }
}
