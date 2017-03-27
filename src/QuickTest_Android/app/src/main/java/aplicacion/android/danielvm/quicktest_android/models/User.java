package aplicacion.android.danielvm.quicktest_android.models;

/**
 * Created by Daniel on 27/03/2017.
 */

public class User {

    private String id;
    private String username;
    private String lastname;
    private String fullname;
    private String email;

    public User(String id, String username, String lastname, String fullname, String email) {
        this.id = id;
        this.username = username;
        this.lastname = lastname;
        this.fullname = fullname;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
