package aplicacion.android.danielvm.quicktestandroid.models.apirest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Clase QuestionTest encarga de realizar las pruebas unitarias sobre la clase Question.
 *
 * @author Daniel Puente Gabarri.
 */

public class QuestionTest {

    @Test
    public void questionTest() {

        int idPregunta = 1;
        String titulo = "titulo de la pregunta";
        int puntuacion = 4;

        Question question = new Question(idPregunta, titulo, puntuacion);

        // Comprobamos el contenido
        assertEquals(idPregunta, question.getIdPregunta());
        assertEquals(titulo, question.getTitulo());
        assertEquals(puntuacion, question.getPuntuacion());


        // Modificamos el contenido
        int idPregunta2 = 12;
        String titulo2 = "titulo de la pregunta modificado";
        int puntuacion2 = 43;

        question.setIdPregunta(idPregunta2);
        question.setTitulo(titulo2);
        question.setPuntuacion(puntuacion2);

        // Comprobamos que la informacion a cambiado
        assertNotEquals(idPregunta, question.getIdPregunta());
        assertNotEquals(titulo, question.getTitulo());
        assertNotEquals(puntuacion, question.getPuntuacion());

        assertEquals(idPregunta2, question.getIdPregunta());
        assertEquals(titulo2, question.getTitulo());
        assertEquals(puntuacion2, question.getPuntuacion());
    }
}
