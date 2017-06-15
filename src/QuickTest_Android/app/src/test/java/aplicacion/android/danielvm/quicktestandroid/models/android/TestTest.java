package aplicacion.android.danielvm.quicktestandroid.models.android;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;


import aplicacion.android.danielvm.quicktestandroid.models.apirest.Answer;

import static org.junit.Assert.*;

/**
 * Clase TestTest encargada de realizar las pruebas unitarias sobre la clase Test.
 *
 * @author Daniel Puente Gabarri.
 */

public class TestTest {

    @Test
    public void testTest() {
        String question = "Pregunta";
        List<Answer> answers = Arrays
                .asList(new Answer(54, "titulo", 0, 234, 20), new Answer(89, "titulo2", 1, 134, 5));
        int idQuestion = 67;
        String wildCard = "verde";

        aplicacion.android.danielvm.quicktestandroid.models.android.Test test =
                new aplicacion.android.danielvm.quicktestandroid.models.android.Test(question, answers, idQuestion);

        //Comprobamos el acceso al contenido
        assertEquals(question, test.getQuestion());
        assertEquals(answers, test.getAnswers());
        assertEquals(idQuestion, test.getIdQuestion());
        assertNotEquals(wildCard, test.getQuestion());
        test.setWildCard(wildCard);
        assertEquals(wildCard, test.getWildCard());

        // Modificamos la informacion
        String question2 = "Pregunta2";
        List<Answer> answers2 = Arrays
                .asList(new Answer(544, "titulo444", 1, 2454, 110), new Answer(829, "111titulo2", 1, 1324, 15));
        int idQuestion2 = 37;
        String wildCard2 = "ambar";

        test.setQuestion(question2);
        test.setAnswers(answers2);
        test.setIdQuestion(idQuestion2);
        test.setWildCard(wildCard2);

        // Comprobamos que la informacion a cambiado

        assertNotEquals(question, test.getQuestion());
        assertNotEquals(answers, test.getAnswers());
        assertNotEquals(idQuestion, test.getIdQuestion());
        assertNotEquals(wildCard, test.getQuestion());

        assertEquals(question2, test.getQuestion());
        assertEquals(answers2, test.getAnswers());
        assertEquals(idQuestion2, test.getIdQuestion());
        assertEquals(wildCard2, test.getWildCard());


    }
}
