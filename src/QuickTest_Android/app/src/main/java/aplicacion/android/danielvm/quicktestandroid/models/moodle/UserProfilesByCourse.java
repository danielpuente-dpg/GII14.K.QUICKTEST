package aplicacion.android.danielvm.quicktestandroid.models.moodle;

import java.util.List;

/**
 * Clase UserProfilesByCourse encargada de encapsular el contenido de la respuesta JSON proporcionada
 * por el web service de Moodle en un objeto Java.
 *
 * @author Daniel Puente Gabarri.
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
