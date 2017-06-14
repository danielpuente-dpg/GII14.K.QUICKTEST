package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Clase ResultTest encargada de realizar las pruebas unitarias sobre la clase Result.
 *
 * @author Daniel Puente Gabarri.
 */

public class ResultTest {

    @Test
    public void resultTest() {

        int idPregunta = 123;
        int idRespuesta = 54;
        String tipoComUsado = "verde";
        String idAlumno = "frasad:43";

        Result result = new Result(idPregunta, idRespuesta, tipoComUsado, idAlumno);

        // Comprobamos el contenido
        assertEquals(idPregunta, result.getIdPregunta());
        assertEquals(idRespuesta, result.getIdRespuesta());
        assertEquals(tipoComUsado, result.getTipoComUsado());
        assertEquals(idAlumno, result.getIdAlumno());

        // Modificamos el contenido
        int idPregunta2 = 321;
        int idRespuesta2 = 34;
        String tipoComUsado2 = "";
        String idAlumno2 = "dsakjbgv:&6";

        result.setIdPregunta(idPregunta2);
        result.setIdRespuesta(idRespuesta2);
        result.setTipoComUsado(tipoComUsado2);
        result.setIdAlumno(idAlumno2);

        // Comprobamos el nuevo contenido

        assertNotEquals(idPregunta, result.getIdPregunta());
        assertNotEquals(idRespuesta, result.getIdRespuesta());
        assertNotEquals(tipoComUsado, result.getTipoComUsado());
        assertNotEquals(idAlumno, result.getIdAlumno());

        assertEquals(idPregunta2, result.getIdPregunta());
        assertEquals(idRespuesta2, result.getIdRespuesta());
        assertEquals(tipoComUsado2, result.getTipoComUsado());
        assertEquals(idAlumno2, result.getIdAlumno());
    }
}
