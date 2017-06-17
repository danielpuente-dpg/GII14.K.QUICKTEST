package aplicacion.android.danielvm.quicktestandroid.models.android;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Clase HelpTest encargada de realizar las pruebas unitarias sobre la clase Help.
 *
 * @author Daniel Puente Gabarri.
 */
public class HelpTest {

    @Test
    public void helpTest() {

        int img = 1;
        String type = "verde";
        String text = "rapido";

        Help help = new Help(img, type, text);

        // Comprobamos el contenido
        assertEquals(img, help.getImg());
        assertEquals(type, help.getType());
        assertEquals(text, help.getText());

        // Modificamos el contenido
        int img2 = 2;
        String type2 = "ambar";
        String text2 = "lento";

        help.setImg(img2);
        help.setType(type2);
        help.setText(text2);

        // Comprobamos el nuevo contenido

        assertNotEquals(img, help.getImg());
        assertNotEquals(type, help.getType());
        assertNotEquals(text, help.getText());

        assertEquals(img2, help.getImg());
        assertEquals(type2, help.getType());
        assertEquals(text2, help.getText());

    }
}
