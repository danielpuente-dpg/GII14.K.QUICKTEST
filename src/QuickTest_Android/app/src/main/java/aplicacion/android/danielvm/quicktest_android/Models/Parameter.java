package aplicacion.android.danielvm.quicktest_android.Models;

/**
 * Created by Daniel on 17/04/2017.
 */

public class Parameter {

    private String name;
    private String value;

    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
