package aplicacion.android.danielvm.quicktestandroid.models.android;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Clase StudentTest encargada de realizar las pruebas unitarias sobre la clase Student.
 *
 * @author Daniel Puente Gabarri.
 */

public class StudentTest {

    @Test
    public void studentTest() {
        int id = 3;
        String firstname = "Daniel";
        String lastname = "Puente Gabarri";
        String fullname = "Daniel Puente Gabarri";
        String email = "dpg0029@alu.ubu.es";
        int status = 0;
        double grade = 6.82;

        Student student = new Student(id, firstname, lastname, fullname, email);

        // Comprobamos el acceso al contenido
        assertEquals(id, student.getId());
        assertEquals(firstname, student.getFirstname());
        assertEquals(lastname, student.getLastname());
        assertEquals(fullname, student.getFullname());
        assertEquals(email, student.getEmail());
        student.setStatus(status);
        student.setGrade(grade);
        assertEquals(status, student.getStatus());
        assertEquals(grade, student.getGrade(), 0);

        // Cambiamos el contenido
        int id2 = 45;
        String firstname2 = "Pepe";
        String lastname2 = "Perez Perez";
        String fullname2 = "Pepe Perez Perez";
        String email2 = "ppp@alu.ubu.es";
        int status2 = 1;
        double grade2 = 0.82;


        student.setId(id2);
        student.setFirstname(firstname2);
        student.setLastname(lastname2);
        student.setFullname(fullname2);
        student.setEmail(email2);
        student.setStatus(status2);
        student.setGrade(grade2);

        // Comprobamos como el contenido a cambiado

        assertNotEquals(id, student.getId());
        assertNotEquals(firstname, student.getFirstname());
        assertNotEquals(lastname, student.getLastname());
        assertNotEquals(fullname, student.getFullname());
        assertNotEquals(email, student.getEmail());
        assertNotEquals(status, student.getStatus());
        assertNotEquals(grade, student.getGrade());

        assertEquals(id2, student.getId());
        assertEquals(firstname2, student.getFirstname());
        assertEquals(lastname2, student.getLastname());
        assertEquals(fullname2, student.getFullname());
        assertEquals(email2, student.getEmail());
        assertEquals(status2, student.getStatus());
        assertEquals(grade2, student.getGrade(), 0);


    }
}
