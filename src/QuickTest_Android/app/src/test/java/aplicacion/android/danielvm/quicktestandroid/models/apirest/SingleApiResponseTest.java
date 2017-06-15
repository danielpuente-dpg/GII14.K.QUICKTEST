package aplicacion.android.danielvm.quicktestandroid.models.apirest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Clase SingleApiResponseTest encargada de realizar las pruebas unitarias sobre
 * la clase SingleApiResponse.
 *
 * @author Daniel Puente Gabarri.
 */

public class SingleApiResponseTest {

    @Test
    public void singleApiResponseTest() {
        int status = 0;
        String message = "Holaa!!!";

        SingleApiResponse singleApiResponse = new SingleApiResponse(status, message);

        // Comprobamos el contenido
        assertEquals(status, singleApiResponse.getStatus());
        assertEquals(message, singleApiResponse.getMessage());

        // Modificamos el contenido
        int status2 = 1;
        String message2 = "Adios!!!";
        singleApiResponse.setStatus(status2);
        singleApiResponse.setMessage(message2);

        // Comprobamos el nuevo contenido
        assertNotEquals(status, singleApiResponse.getStatus());
        assertNotEquals(message, singleApiResponse.getMessage());

        assertEquals(status2, singleApiResponse.getStatus());
        assertEquals(message2, singleApiResponse.getMessage());

    }
}
