package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

/**
 * Clase Course encargada de encapsular el contenido de la respuesta JSON proporcionada
 * por el web service de Moodle en un objeto Java.
 *
 * @autor Daniel Puente Gabarri.
 */

public class Course {
    private int id;
    private String shortname;
    private String fullname;
    private String rol;

    public Course(int id, String shortname, String fullname) {
        this.id = id;
        this.shortname = shortname;
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
