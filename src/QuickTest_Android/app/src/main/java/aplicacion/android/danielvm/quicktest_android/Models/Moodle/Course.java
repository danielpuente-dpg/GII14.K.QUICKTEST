package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

/**
 * Created by Daniel on 27/04/2017.
 */

public class Course {
    private int id;
    private String shortname;
    private String fullname;

    public Course() {
    }

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
}
