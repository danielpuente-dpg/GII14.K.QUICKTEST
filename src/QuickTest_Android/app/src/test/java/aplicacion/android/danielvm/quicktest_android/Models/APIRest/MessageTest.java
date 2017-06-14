package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Clase MessageTest encargada realizar las pruebas unitarias sobre la clase Message.
 *
 * @autor Daniel Puente Gabarri.
 */

public class MessageTest {

    @Test
    public void messageTest() {
        Question question = new Question(12, "titulo", 4);
        List<Answer> answers = Arrays
                .asList(new Answer(34, "tituloRes", 1, 2, 3), new Answer(35, "tituloDeRes", 4, 5, 6));

        Message message = new Message(question, answers);

        // Comprobamos el acceso al contenido
        assertEquals(question, message.getQuestion());
        assertEquals(answers, message.getAnswers());

        // Modificamos la informacion
        Question question2 = new Question(222, "titulo2", 43);
        List<Answer> answers2 = Arrays
                .asList(new Answer(3344, "tituloRes2", 11, 22, 33), new Answer(3553, "tituloDeRes2", 44, 55, 66));

        message.setQuestion(question2);
        message.setAnswers(answers2);

        // Comprobamos el nuevo contentido

        assertNotEquals(question, message.getQuestion());
        assertNotEquals(answers, message.getAnswers());

        assertEquals(question2, message.getQuestion());
        assertEquals(answers2, message.getAnswers());

    }
}
