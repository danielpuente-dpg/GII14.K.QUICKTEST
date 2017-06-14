package aplicacion.android.danielvm.quicktest_android.Models.Moodle;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Clase ModuleTest encargada de realizar las pruebas unitarias sobre la clase Module.
 *
 * @author Daniel Puente Gabarri.
 */

public class ModuleTest {

    @Test
    public void moduleTest() {
        int id = 1;
        String name = "pepe";
        String modname = "pepemod";

        Module module = new Module(id, name, modname);

        // Comprobamos el contenido
        assertEquals(id, module.getId());
        assertEquals(name, module.getName());
        assertEquals(modname, module.getModname());

        // Modificamos el contenido
        int id2 = 12;
        String name2 = "pepe2";
        String modname2 = "pepemod2";

        module.setId(id2);
        module.setName(name2);
        module.setModname(modname2);

        // Compronamos el nuevo contenido

        assertNotEquals(id, module.getId());
        assertNotEquals(name, module.getName());
        assertNotEquals(modname, module.getModname());


        assertEquals(id2, module.getId());
        assertEquals(name2, module.getName());
        assertEquals(modname2, module.getModname());
    }
}
