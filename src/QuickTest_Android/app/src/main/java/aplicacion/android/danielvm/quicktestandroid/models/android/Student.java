package aplicacion.android.danielvm.quicktestandroid.models.android;

/**
 * Clase Student encapsular el contenido de la respuesta JSON proporcionada
 * por el web service de Moodle en un objeto Java.
 *
 * @author Daniel Puente Gabarri.
 */

public class Student {
    private int id;
    private String firstname;
    private String lastname;
    private String fullname;
    private String email;
    private int status;
    private double grade;

    public Student(int id, String firstname, String lastname, String fullname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullname = fullname;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
