package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Clase EnrolCourseTest encargada de realizar las pruebas unitarias sobre la clase EnrolCourse.
 *
 * @author Daniel Puente Gabarri.
 */

public class EnrolCourseTest {

    @Test
    public void enrolCourseTest() {
        int id = 1;
        String fullname = "pepe pep";
        String shortname = "pep";

        EnrolCourse enrolCourse = new EnrolCourse(id, fullname, shortname);

        // Comprobamos el contenido
        assertEquals(id, enrolCourse.getId());
        assertEquals(fullname, enrolCourse.getFullname());
        assertEquals(shortname, enrolCourse.getShortname());

        // Modificamos el contenido
        int id2 = 12;
        String fullname2 = "pepe pep222";
        String shortname2 = "pep222";

        enrolCourse.setId(id2);
        enrolCourse.setFullname(fullname2);
        enrolCourse.setShortname(shortname2);
        // Comprobamos el nuevo contenido

        assertNotEquals(id, enrolCourse.getId());
        assertNotEquals(fullname, enrolCourse.getFullname());
        assertNotEquals(shortname, enrolCourse.getShortname());


        assertEquals(id2, enrolCourse.getId());
        assertEquals(fullname2, enrolCourse.getFullname());
        assertEquals(shortname2, enrolCourse.getShortname());
    }
}
