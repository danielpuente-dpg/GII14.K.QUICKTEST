package aplicacion.android.danielvm.quicktestandroid.models.moodle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Clase TokenTest encargada de realizar las pruebas unitarias sobre la clase Token.
 *
 * @author Daniel Puente Gabarri.
 */

public class TokenTest {

    @Test
    public void tokenTest() {
        String token = "u4nhr3c4wct";
        String privatetoken = "lkanlasngshir40t";

        Token token1 = new Token(token, privatetoken);

        // Comprobamos el contenido
        assertEquals(token, token1.getToken());
        assertEquals(privatetoken, token1.getPrivatetoken());

        /// Modificamos el contenido

        String token2 = "48np95ypw6y43";
        String privatetoken2 = "oe85u6ñvw3";

        token1.setToken(token2);
        token1.setPrivatetoken(privatetoken2);

        // Comprobamos el nuevo contenido

        assertNotEquals(token, token1.getToken());
        assertNotEquals(privatetoken, token1.getPrivatetoken());

        assertEquals(token2, token1.getToken());
        assertEquals(privatetoken2, token1.getPrivatetoken());


    }
}
