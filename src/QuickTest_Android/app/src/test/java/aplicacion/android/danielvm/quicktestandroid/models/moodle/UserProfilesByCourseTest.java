package aplicacion.android.danielvm.quicktestandroid.models.moodle;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Clase UserProfilesByCourseTest encargada de realizar las pruebas unitarias sobre
 * la clase UserProfilesByCourse.
 *
 * @author Daniel Puente Gabarri.
 */

public class UserProfilesByCourseTest {

    @Test
    public void userProfilesByCourseTest(){
        List<Role> roles = Arrays
                .asList(new Role(1, "name", "shortname", 1), new Role(2, "name2", "shortname2", 2));

        UserProfilesByCourse userProfilesByCourse = new UserProfilesByCourse(roles);

        // Comprobamos el contenido
        assertEquals(roles, userProfilesByCourse.getRoles());

        // Modificamos el contenido
        List<Role> roles2 = Arrays
                .asList(new Role(3, "name3", "shortname3", 3), new Role(4, "name4", "shortname4", 4));

        userProfilesByCourse.setRoles(roles2);
        // Comprobamos el nuevo contenido
        assertNotEquals(roles, userProfilesByCourse.getRoles());

        assertEquals(roles2, userProfilesByCourse.getRoles());

    }
}
