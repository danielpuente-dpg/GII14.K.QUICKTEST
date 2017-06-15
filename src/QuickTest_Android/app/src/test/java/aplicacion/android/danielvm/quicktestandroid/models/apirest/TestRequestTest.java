package aplicacion.android.danielvm.quicktestandroid.models.apirest;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Clase TestRequestTest encargada de realizar las pruenas unitarias sobre la clase TestRequest.
 *
 * @author Daniel Puente Gabarri.
 */

public class TestRequestTest {

    @Test
    public void testRequestTest() {
        int idCuestionario = 1;
        String idAlumno = "1";
        String nombreAlu = "Daniel";
        String apeAlu = "Puente";
        List<Result> respuestas = Arrays
                .asList(new Result(1, 2, "verde", "sdafasd:45"), new Result(3, 4, "verde", "dskljf:12"));

        TestRequest testRequest =
                new TestRequest(idCuestionario, idAlumno, nombreAlu, apeAlu, respuestas);

        // Comprobamos el contenido
        assertEquals(idCuestionario, testRequest.getIdCuestionario());
        assertEquals(idAlumno, testRequest.getIdAlumno());
        assertEquals(nombreAlu, testRequest.getNombreAlu());
        assertEquals(apeAlu, testRequest.getApeAlu());
        assertEquals(respuestas, testRequest.getRespuestas());

        // Modificamos el contenido
        int idCuestionario2 = 2;
        String idAlumno2 = "2";
        String nombreAlu2 = "Pepe";
        String apeAlu2 = "Perez";
        List<Result> respuestas2 = Arrays
                .asList(new Result(11, 22, "verde", "sdafasd:45"), new Result(33, 44, "verde", "dskljf:12"));

        testRequest.setIdCuestionario(idCuestionario2);
        testRequest.setIdAlumno(idAlumno2);
        testRequest.setNombreAlu(nombreAlu2);
        testRequest.setApeAlu(apeAlu2);
        testRequest.setRespuestas(respuestas2);

        // Comprobamos que el nuevo contenido

        assertNotEquals(idCuestionario, testRequest.getIdCuestionario());
        assertNotEquals(idAlumno, testRequest.getIdAlumno());
        assertNotEquals(nombreAlu, testRequest.getNombreAlu());
        assertNotEquals(apeAlu, testRequest.getApeAlu());
        assertNotEquals(respuestas, testRequest.getRespuestas());


        assertEquals(idCuestionario2, testRequest.getIdCuestionario());
        assertEquals(idAlumno2, testRequest.getIdAlumno());
        assertEquals(nombreAlu2, testRequest.getNombreAlu());
        assertEquals(apeAlu2, testRequest.getApeAlu());
        assertEquals(respuestas2, testRequest.getRespuestas());


    }
}
