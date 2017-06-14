package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

/**
 * Clase EnrolCourse encargada de encapsular el contenido de la respuesta JSON proporcionada
 * por el web service de Moodle en un objeto Java.
 *
 * @autor Daniel Puente Gabarri.
 */

public class EnrolCourse {
    private int id;
    private String fullname;
    private String shortname;

    public EnrolCourse(int id, String fullname, String shortname) {
        this.id = id;
        this.fullname = fullname;
        this.shortname = shortname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
}
