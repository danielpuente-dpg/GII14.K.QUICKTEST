package aplicacion.android.danielvm.quicktestandroid.models.moodle;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

/**
 * Clase UserEnrolTest encargada de realizar las pruebas unitarias sobre la clase UserEnrol.
 *
 * @author Daniel Puente Gabarri.
 */

public class UserEnrolTest {

    @Test
    public void userEnrolTest() {

        int idUser = 1;
        String username = "alumno";
        String firstname = "nombre";
        String lastname = "apellido";
        String fullname = "nombre apellido";
        String email = "alumno@alu.ubu.es";
        List<Role> roles = Arrays
                .asList(new Role(1, "name", "shortname", 1), new Role(2, "name2", "shortname2", 2));
        List<EnrolCourse> enrolledcourses = Arrays
                .asList(new EnrolCourse(1, "fullname", "shortname"));

        UserEnrol userEnrol =
                new UserEnrol(idUser, username, firstname, lastname, fullname, email, roles, enrolledcourses);

        // Comprobamps el contenido

        assertEquals(idUser, userEnrol.getIdUser());
        assertEquals(username, userEnrol.getUsername());
        assertEquals(firstname, userEnrol.getFirstname());
        assertEquals(lastname, userEnrol.getLastname());
        assertEquals(fullname, userEnrol.getFullname());
        assertEquals(email, userEnrol.getEmail());
        assertEquals(roles, userEnrol.getRoles());
        assertEquals(enrolledcourses, userEnrol.getEnrolledcourses());

        // Modificamos el contenido

        int idUser2 = 12;
        String username2 = "alumno2";
        String firstname2 = "nombre2";
        String lastname2 = "apellido2";
        String fullname2 = "nombre apellido2";
        String email2 = "alumno@alu.ubu.es2";
        List<Role> roles2 = Arrays
                .asList(new Role(11, "name2", "shortname2", 11), new Role(22, "name22", "shortname22", 22));
        List<EnrolCourse> enrolledcourses2 = Arrays
                .asList(new EnrolCourse(11, "fullname1", "shortname1"));

        userEnrol.setIdUser(idUser2);
        userEnrol.setUsername(username2);
        userEnrol.setFirstname(firstname2);
        userEnrol.setLastname(lastname2);
        userEnrol.setFullname(fullname2);
        userEnrol.setEmail(email2);
        userEnrol.setRoles(roles2);
        userEnrol.setEnrolledcourses(enrolledcourses2);


        // Comprobamos el nuevo contenido
        assertNotEquals(idUser, userEnrol.getIdUser());
        assertNotEquals(username, userEnrol.getUsername());
        assertNotEquals(firstname, userEnrol.getFirstname());
        assertNotEquals(lastname, userEnrol.getLastname());
        assertNotEquals(fullname, userEnrol.getFullname());
        assertNotEquals(email, userEnrol.getEmail());
        assertNotEquals(roles, userEnrol.getRoles());
        assertNotEquals(enrolledcourses, userEnrol.getEnrolledcourses());


        assertEquals(idUser2, userEnrol.getIdUser());
        assertEquals(username2, userEnrol.getUsername());
        assertEquals(firstname2, userEnrol.getFirstname());
        assertEquals(lastname2, userEnrol.getLastname());
        assertEquals(fullname2, userEnrol.getFullname());
        assertEquals(email2, userEnrol.getEmail());
        assertEquals(roles2, userEnrol.getRoles());
        assertEquals(enrolledcourses2, userEnrol.getEnrolledcourses());

    }
}
