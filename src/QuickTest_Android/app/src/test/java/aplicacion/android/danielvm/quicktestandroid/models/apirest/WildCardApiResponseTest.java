package aplicacion.android.danielvm.quicktestandroid.models.apirest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Clase WildCardApiResponseTest encargada de realizar las pruebas unitarias sobre
 * la clase WildCardApiResponse.
 *
 * @author Daniel Puente Gabarri
 */

public class WildCardApiResponseTest {

    @Test
    public void wildCardApiResponseTest() {
        int status = 0;
        List<WildCard> wildCards = Arrays.asList(new WildCard(1, 1), new WildCard(2, 2));

        WildcardApiResponse wildcardApiResponse = new WildcardApiResponse(status, wildCards);

        // Comprobamos el contenido
        assertEquals(status, wildcardApiResponse.getStatus());
        assertEquals(wildCards, wildcardApiResponse.getWildCards());

        // Modificamos el contenido
        int status2 = 1;
        List<WildCard> wildCards2 = Arrays.asList(new WildCard(11, 11), new WildCard(22, 22));

        wildcardApiResponse.setStatus(status2);
        wildcardApiResponse.setWildCards(wildCards2);

        // Comprobamos el nuevo contenido

        assertNotEquals(status, wildcardApiResponse.getStatus());
        assertNotEquals(wildCards, wildcardApiResponse.getWildCards());

        assertEquals(status2, wildcardApiResponse.getStatus());
        assertEquals(wildCards2, wildcardApiResponse.getWildCards());
    }
}
