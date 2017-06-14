package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Clase CourseTest encargada de realizar las pruebas unitarias sobre la clase Course.
 *
 * @author Daniel Puente Gabarri.
 */

public class CourseTest {

    @Test
    public void courseTest() {
        int id = 1;
        String shortname = "Pepito";
        String fullname = "Pepito perez";
        String rol = "";

        Course course = new Course(id, shortname, fullname);

        // Comprobamos el contenido
        assertEquals(id, course.getId());
        assertEquals(shortname, course.getShortname());
        assertEquals(fullname, course.getFullname());
        course.setRol(rol);
        assertEquals(rol, course.getRol());

        // Modificamos el contenido
        int id2 = 12;
        String shortname2 = "Pepito2";
        String fullname2 = "Pepito perez222";
        String rol2 = "student";

        course.setId(id2);
        course.setShortname(shortname2);
        course.setFullname(fullname2);
        course.setRol(rol2);

        // Comprobamos el nuevo contenido

        assertNotEquals(id, course.getId());
        assertNotEquals(shortname, course.getShortname());
        assertNotEquals(fullname, course.getFullname());
        assertNotEquals(rol, course.getRol());

        assertEquals(id2, course.getId());
        assertEquals(shortname2, course.getShortname());
        assertEquals(fullname2, course.getFullname());
        assertEquals(rol2, course.getRol());
    }
}
