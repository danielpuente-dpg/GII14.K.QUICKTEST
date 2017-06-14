package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

/**
 * Clase Module encargada de encapsular el contenido de la respuesta JSON proporcionada
 * por el web service de Moodle en un objeto Java.
 *
 * @autor Daniel Puente Gabarri.
 */

public class Module {
    private int id;
    private String name;
    private String modname;

    public Module(){}

    public Module(int id, String name, String modname) {
        this.id = id;
        this.name = name;
        this.modname = modname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModname() {
        return modname;
    }

    public void setModname(String modname) {
        this.modname = modname;
    }
}
