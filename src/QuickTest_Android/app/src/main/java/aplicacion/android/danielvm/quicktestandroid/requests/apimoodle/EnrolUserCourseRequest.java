package aplicacion.android.danielvm.quicktestandroid.requests.apimoodle;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import aplicacion.android.danielvm.quicktestandroid.api.APIMoodle;
import aplicacion.android.danielvm.quicktestandroid.api.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktestandroid.models.moodle.Course;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase EnrolUserCourseRequest encargada de obtener los usuarios que se encuentran
 * matriculados en un curso.
 *
 * @author Daniel Puente Gabarri.
 */

public class EnrolUserCourseRequest extends AsyncTask<Void, Void, Course[]> {
    private Retrofit retrofit;
    private String token;
    private int userId;

    /**
     * Constructor de la clase.
     *
     * @param retrofit, retrofit.
     * @param token,    token del web service.
     * @param userId,   identidicados del usuario.
     */
    public EnrolUserCourseRequest(Retrofit retrofit, String token, int userId) {
        this.retrofit = retrofit;
        this.token = token;
        this.userId = userId;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     *
     * @param params, params.
     * @return, Course[], courses.
     */
    @Override
    protected Course[] doInBackground(Void... params) {
        Course[] course = null;

        MoodleService service = retrofit.create(MoodleService.class);
        Call<Course[]> call = service.getCoursesByUserId(token, APIMoodle.GET_COURSES_BY_USER_ID, APIMoodle.FORMAT_JSON, userId);

        try {
            course = call.execute().body();
        } catch (IOException e) {
            Log.d("EnrolUserCourseRequest", e.getMessage());
        }

        return course;
    }
}
