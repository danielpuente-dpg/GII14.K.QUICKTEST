package aplicacion.android.danielvm.quicktestandroid.models.moodle;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Clase ExternalToolTest encargada de realizar las pruebas unitarias osbre la clase ExternalTool.
 *
 * @author Daniel Puente Gabarri.
 */

public class ExternalToolTest {

    @Test
    public void externalToolTest(){

        String endpoint = "qwert";
        List<Parameter> parameters = Arrays
                .asList(new Parameter("name", "value"), new Parameter("name2", "value2"));

        ExternalTool externalTool = new ExternalTool(endpoint, parameters);

        // Comprobamos el contenido

        assertEquals(endpoint, externalTool.getEndpoint());
        assertEquals(parameters, externalTool.getParameters());

        // Modificamos el contenido
        String endpoint2 = "asdfghj";
        List<Parameter> parameters2 = Arrays
                .asList(new Parameter("name3", "value3"), new Parameter("name4", "value4"));

        externalTool.setEndpoint(endpoint2);
        externalTool.setParameters(parameters2);

        // Comprobamos el nuevo contenido
        assertNotEquals(endpoint, externalTool.getEndpoint());
        assertNotEquals(parameters, externalTool.getParameters());

        assertEquals(endpoint2, externalTool.getEndpoint());
        assertEquals(parameters2, externalTool.getParameters());
    }
}
