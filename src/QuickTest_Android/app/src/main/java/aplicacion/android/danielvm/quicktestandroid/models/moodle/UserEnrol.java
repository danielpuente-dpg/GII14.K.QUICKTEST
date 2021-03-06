package aplicacion.android.danielvm.quicktestandroid.models.moodle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Clase UserEnrol encargada de encapsular el contenido de la respuesta JSON proporcionada
 * por el web service de Moodle en un objeto Java.
 *
 * @author Daniel Puente Gabarri.
 */

public class UserEnrol {
    @SerializedName("id")
    private int idUser;
    private String username;
    private String firstname;
    private String lastname;
    private String fullname;
    private String email;
    private List<Role> roles;
    private List<EnrolCourse> enrolledcourses;

    public UserEnrol(int idUser, String username, String firstname, String lastname, String fullname, String email, List<Role> roles, List<EnrolCourse> enrolledcourses) {
        this.idUser = idUser;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullname = fullname;
        this.email = email;
        this.roles = roles;
        this.enrolledcourses = enrolledcourses;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<EnrolCourse> getEnrolledcourses() {
        return enrolledcourses;
    }

    public void setEnrolledcourses(List<EnrolCourse> enrolledcourses) {
        this.enrolledcourses = enrolledcourses;
    }
}
