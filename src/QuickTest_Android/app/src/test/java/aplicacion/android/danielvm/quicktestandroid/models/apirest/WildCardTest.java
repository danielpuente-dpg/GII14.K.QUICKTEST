package aplicacion.android.danielvm.quicktestandroid.models.apirest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Clase WildCardTest encargada de realizar las pruebas unitarias sobre la clase WildCard.
 *
 * @author Daniel Puente Gabarri.
 */

public class WildCardTest {

    @Test
    public void wildCardTest() {
        int idQuestion = 1;
        int idAnswer = 1;

        WildCard wildCard = new WildCard(idQuestion, idAnswer);

        // Comprobamos el contenido
        assertEquals(idQuestion, wildCard.getIdQuestion());
        assertEquals(idAnswer, wildCard.getIdAnswer());

        // Modificamos el contenido
        int idQuestion2 = 12;
        int idAnswer2 = 12;

        wildCard.setIdQuestion(idQuestion2);
        wildCard.setIdAnswer(idAnswer2);

        // Comprobamos el nuevo contenido
        assertNotEquals(idQuestion, wildCard.getIdQuestion());
        assertNotEquals(idAnswer, wildCard.getIdAnswer());

        assertEquals(idQuestion2, wildCard.getIdQuestion());
        assertEquals(idAnswer2, wildCard.getIdAnswer());
    }
}
