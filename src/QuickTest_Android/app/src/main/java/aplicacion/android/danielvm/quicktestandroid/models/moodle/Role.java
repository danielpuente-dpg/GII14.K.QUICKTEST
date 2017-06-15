package aplicacion.android.danielvm.quicktestandroid.models.moodle;

/**
 * Clase Role encargada de encapsular el contenido de la respuesta JSON proporcionada
 * por el web service de Moodle en un objeto Java.
 *
 * @author Daniel Puente Gabarri.
 */

public class Role {
    private int roleid;
    private String name;
    private String shortname;
    private int sortorder;

    public Role(int roleid, String name, String shortname, int sortorder) {
        this.roleid = roleid;
        this.name = name;
        this.shortname = shortname;
        this.sortorder = sortorder;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public int getSortorder() {
        return sortorder;
    }

    public void setSortorder(int sortorder) {
        this.sortorder = sortorder;
    }
}
