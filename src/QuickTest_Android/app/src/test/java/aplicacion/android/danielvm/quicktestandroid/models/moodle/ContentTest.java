package aplicacion.android.danielvm.quicktestandroid.models.moodle;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Clase ContentTest encargada de realizar las pruebas unitarias sobre la clase Content.
 *
 * @author Daniel Puente Gabarri.
 */

public class ContentTest {

    @Test
    public void contentTest(){
        int id = 1;
        String name = "nombre";
        List<Module> modules = Arrays
                .asList(new Module(1,"pepe","modPepe"), new Module(2,"pepon","modPepon"));

        Content content = new Content(id, name, modules);

        // Comprobamos el contenido
        assertEquals(id, content.getId());
        assertEquals(name, content.getName());
        assertEquals(modules, content.getModules());

        // Modificamos el contenido
        int id2 = 2;
        String name2 = "nombre2";
        List<Module> modules2 = Arrays
                .asList(new Module(11,"pepe2","modPepe2"), new Module(22,"pepon2","modPepon2"));

        content.setId(id2);
        content.setName(name2);
        content.setModules(modules2);

        // Comprobamos el nuevo contenido

        assertNotEquals(id, content.getId());
        assertNotEquals(name, content.getName());
        assertNotEquals(modules, content.getModules());


        assertEquals(id2, content.getId());
        assertEquals(name2, content.getName());
        assertEquals(modules2, content.getModules());


    }
}
