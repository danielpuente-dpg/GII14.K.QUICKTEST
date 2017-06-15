package aplicacion.android.danielvm.quicktestandroid.models.moodle;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Clase ParameterTest encargada de realizar las pruebas unitarias sobre la clase Parameter.
 *
 * @author Daniel Puente Gabarri.
 */

public class ParameterTest {

    @Test
    public void parameterTest() {
        String name = "name";
        String value = "value";

        Parameter parameter = new Parameter(name, value);

        // Comprobamos el contenido
        assertEquals(name, parameter.getName());
        assertEquals(value, parameter.getValue());

        // Modificamos el contenido
        String name2 = "name2";
        String value2 = "value2";

        parameter.setName(name2);
        parameter.setValue(value2);

        // Comprobamos el nuevo contenido

        assertNotEquals(name, parameter.getName());
        assertNotEquals(value, parameter.getValue());

        assertEquals(name2, parameter.getName());
        assertEquals(value2, parameter.getValue());
    }
}
