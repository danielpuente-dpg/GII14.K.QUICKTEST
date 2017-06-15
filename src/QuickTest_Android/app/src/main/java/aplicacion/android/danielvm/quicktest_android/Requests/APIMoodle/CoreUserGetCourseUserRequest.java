package aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Content;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Module;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Role;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.UserProfilesByCourse;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase CoreUserGetCourseUserRequest encargada de obtener los usuarios de un curso.
 *
 * @author Daniel Puente Gabarri.
 */

public class CoreUserGetCourseUserRequest extends AsyncTask<Void, Void, List<Role>> {

    private Retrofit retrofit;
    private String token;
    private int idUser;
    private int idCourse;

    /**
     * Constructor de la clase.
     *
     * @param retrofit, retrofit.
     * @param token,    token del web service.
     * @param idUser,   identificador del usuario.
     * @param idCourse, identificador del curso.
     */
    public CoreUserGetCourseUserRequest(Retrofit retrofit, String token, int idUser, int idCourse) {
        this.retrofit = retrofit;
        this.token = token;
        this.idUser = idUser;
        this.idCourse = idCourse;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     *
     * @param params, params.
     * @return List<Role>, roles.
     */
    @Override
    protected List<Role> doInBackground(Void... params) {
        List<Role> roles = null;
        MoodleService service = retrofit.create(MoodleService.class);
        Call<UserProfilesByCourse[]> call = service.getUserGetCourse(token, APIMoodle.GET_PROFILES_USER_BY_USER, APIMoodle.FORMAT_JSON, idUser, idCourse);

        try {
            roles = call.execute().body()[0].getRoles();
        } catch (IOException e) {
            Log.d("CoreUserGetCourseUser", "Request: " + e.getMessage());
        }

        return roles;
    }
}
