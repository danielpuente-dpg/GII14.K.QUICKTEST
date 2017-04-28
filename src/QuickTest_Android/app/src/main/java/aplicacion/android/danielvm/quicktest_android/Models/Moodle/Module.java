package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

/**
 * Created by Daniel on 27/04/2017.
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
