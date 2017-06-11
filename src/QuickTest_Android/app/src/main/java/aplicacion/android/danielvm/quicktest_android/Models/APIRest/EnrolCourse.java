package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

/**
 * Created by Daniel on 11/06/2017.
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
