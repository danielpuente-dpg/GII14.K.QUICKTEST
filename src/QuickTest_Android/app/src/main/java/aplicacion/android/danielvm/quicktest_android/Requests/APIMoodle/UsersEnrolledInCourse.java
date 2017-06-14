package aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.UserEnrol;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase UsersEnrolledInCourse encargada de obtener los usuarios matriculados
 * en un curso.
 *
 * @autor Daniel Puente Gabarri.
 */

public class UsersEnrolledInCourse extends AsyncTask<Void, Void, UserEnrol[]> {

    private Retrofit retrofit;
    private String token;
    private int idCourse;

    /**
     * Constructor de la clase.
     *
     * @param retrofit, retrofit.
     * @param token,    token del web service.
     * @param idCourse, identificador del curso.
     */
    public UsersEnrolledInCourse(Retrofit retrofit, String token, int idCourse) {
        this.retrofit = retrofit;
        this.token = token;
        this.idCourse = idCourse;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     *
     * @param params, params.
     * @return UserEnrol[], userEnrol.
     */
    @Override
    protected UserEnrol[] doInBackground(Void... params) {

        UserEnrol[] retorno = null;

        MoodleService service = retrofit.create(MoodleService.class);
        Call<UserEnrol[]> call = service.getUsersEnrolledInCourse(token, APIMoodle.GET_USERS_ENROLLED_IN_COURSE, APIMoodle.FORMAT_JSON, idCourse);

        try {
            retorno = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }
}