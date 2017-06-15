package aplicacion.android.danielvm.quicktestandroid.models.apirest;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase AnswerTest encargada de realizar los test unitarios sobre la clase Answer.
 *
 * @author Daniel Puente Gabarri.
 */

public class AnswerTest {

    @Test
    public void answerTest(){
        int idRespuesta = 23;
        String titulo = "titulo";
        int esCorrecta = 1;
        int idPregunta = 223;
        int contador = 12;
        Answer answer = new Answer(idRespuesta, titulo, esCorrecta, idPregunta, contador);

        // Comprobamos el acceso al contenido
        assertEquals(idRespuesta, answer.getIdRespuesta());
        assertEquals(titulo, answer.getTitulo());
        assertEquals(esCorrecta, answer.getEsCorrecta());
        assertEquals(idPregunta, answer.getIdPregunta());
        assertEquals(contador, answer.getContador());

        // Modificamos la informacion
        int idRespuesta2 = 2333;
        String titulo2 = "titulo222";
        int esCorrecta2 = 0;
        int idPregunta2 = 22113;
        int contador2 = 112;

        answer.setIdRespuesta(idRespuesta2);
        answer.setTitulo(titulo2);
        answer.setEsCorrecta(esCorrecta2);
        answer.setIdPregunta(idPregunta2);
        answer.setContador(contador2);

        // Comprobamos que la informacion ha cambiado
        assertNotEquals(idRespuesta, answer.getIdRespuesta());
        assertNotEquals(titulo, answer.getTitulo());
        assertNotEquals(esCorrecta, answer.getEsCorrecta());
        assertNotEquals(idPregunta, answer.getIdPregunta());
        assertNotEquals(contador, answer.getContador());

        assertEquals(idRespuesta2, answer.getIdRespuesta());
        assertEquals(titulo2, answer.getTitulo());
        assertEquals(esCorrecta2, answer.getEsCorrecta());
        assertEquals(idPregunta2, answer.getIdPregunta());
        assertEquals(contador2, answer.getContador());
    }
}
