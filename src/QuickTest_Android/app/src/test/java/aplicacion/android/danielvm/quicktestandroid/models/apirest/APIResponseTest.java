package aplicacion.android.danielvm.quicktestandroid.models.apirest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Clase APIResponseTest encarga de realizar las pruebas unitarias sobre la clase APIResponse.
 *
 * @author Daniel Puente Gabarri.
 */

public class APIResponseTest {

    @Test
    public void apiResponseTest() {

        int status = 1;

        List<Message> messages = Arrays
                .asList(new Message(new Question(1, "titulo", 1)
                                , Arrays.asList(new Answer(1, "titulo", 1, 1, 1)
                                , new Answer(2, "titulo2", 2, 2, 2))),
                        new Message(new Question(2, "titulo2", 2)
                                , Arrays.asList(new Answer(3, "titulo3", 3, 3, 3)
                                , new Answer(4, "titulo4", 4, 4, 4))));

        APIResponse apiResponse = new APIResponse(status, messages);

        // Comprobamos el contenido
        assertEquals(status, apiResponse.getStatus());
        assertEquals(messages, apiResponse.getMessages());

        // Modificamos el contenido
        int status2 = 0;

        List<Message> messages2 = Arrays
                .asList(new Message(new Question(11, "titulo1", 11)
                                , Arrays.asList(new Answer(11, "titulo1", 11, 11, 11)
                                , new Answer(22, "titulo22", 22, 22, 22))),
                        new Message(new Question(22, "titulo22", 22)
                                , Arrays.asList(new Answer(33, "titulo33", 33, 33, 33)
                                , new Answer(44, "titulo44", 44, 44, 44))));

        apiResponse.setStatus(status2);
        apiResponse.setMessages(messages2);

        // Comprobamos el nuevo contenido
        assertNotEquals(status, apiResponse.getStatus());
        assertNotEquals(messages, apiResponse.getMessages());


        assertEquals(status2, apiResponse.getStatus());
        assertEquals(messages2, apiResponse.getMessages());
    }
}
