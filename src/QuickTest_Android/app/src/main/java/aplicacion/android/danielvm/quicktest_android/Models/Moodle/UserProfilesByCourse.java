package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

import java.util.List;

/**
 * Created by Daniel on 09/06/2017.
 */

public class UserProfilesByCourse {
    private List<Role> roles;

    public UserProfilesByCourse(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
