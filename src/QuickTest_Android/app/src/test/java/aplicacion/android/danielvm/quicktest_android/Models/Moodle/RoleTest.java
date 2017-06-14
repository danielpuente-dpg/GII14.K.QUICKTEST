package aplicacion.android.danielvm.quicktest_android.Models.Moodle;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Clase RoleTest encargada de realizar las pruebas unitarias sobre la clse Role.
 *
 * @author Daniel Puente Gabarii.
 */

public class RoleTest {

    @Test
    public void roleTest() {
        int roleid = 5;
        String name = "student";
        String shortname = "std";
        int sortorder = 5;

        Role role = new Role(roleid, name, shortname, sortorder);

        // Comprobamos el contenido
        assertEquals(roleid, role.getRoleid());
        assertEquals(name, role.getName());
        assertEquals(shortname, role.getShortname());
        assertEquals(sortorder, role.getSortorder());

        // Modificamos el contenido
        int roleid2 = 54;
        String name2 = "student222";
        String shortname2 = "std22";
        int sortorder2 = 53;

        role.setRoleid(roleid2);
        role.setName(name2);
        role.setShortname(shortname2);
        role.setSortorder(sortorder2);

        // Comprobamos el nuevo contenido
        assertNotEquals(role, role.getRoleid());
        assertNotEquals(name, role.getName());
        assertNotEquals(shortname, role.getShortname());
        assertNotEquals(sortorder, role.getSortorder());

        assertEquals(roleid2, role.getRoleid());
        assertEquals(name2, role.getName());
        assertEquals(shortname2, role.getShortname());
        assertEquals(sortorder2, role.getSortorder());
    }
}
