package aplicacion.android.danielvm.quicktestandroid.requests.apimoodle;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import aplicacion.android.danielvm.quicktestandroid.api.APIMoodle;
import aplicacion.android.danielvm.quicktestandroid.api.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktestandroid.models.moodle.Course;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase CourseRequest encargada de obtener todos los cursos.
 *
 * @author Daniel Puente Gabarri.
 */

public class CourseRequest extends AsyncTask<Void, Void, List<Course>> {
    private Retrofit retrofit;
    private String token;

    /**
     * Constructor de la clase.
     *
     * @param retrofit, retrofit.
     * @param token,    token del web service
     */
    public CourseRequest(Retrofit retrofit, String token) {
        this.retrofit = retrofit;
        this.token = token;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     *
     * @param params, params.
     * @return courses.
     */
    @Override
    protected List<Course> doInBackground(Void... params) {
        List<Course> courses = null;
        MoodleService service = retrofit.create(MoodleService.class);
        Call<List<Course>> call = service.getCourses(token, APIMoodle.GET_COURSES, APIMoodle.FORMAT_JSON);

        try {
            courses = call.execute().body();
        } catch (IOException e) {
            Log.d("CourseRequest", e.getMessage());
        }

        return courses;
    }
}
