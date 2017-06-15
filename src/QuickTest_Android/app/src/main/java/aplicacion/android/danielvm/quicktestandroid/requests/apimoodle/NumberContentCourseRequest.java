package aplicacion.android.danielvm.quicktestandroid.requests.apimoodle;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import aplicacion.android.danielvm.quicktestandroid.api.APIMoodle;
import aplicacion.android.danielvm.quicktestandroid.api.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktestandroid.models.moodle.Content;
import aplicacion.android.danielvm.quicktestandroid.models.moodle.Module;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase NumberContentCourseRequest encargada de obtener el numero de herramientas externas.
 *
 * @author Daniel Puente Gabarri.
 */

public class NumberContentCourseRequest extends AsyncTask<Void, Void, Integer> {

    private Retrofit retrofit;
    private String token;
    private int idCourse;
    private int cont;

    /**
     * Constructor de la clase.
     *
     * @param retrofit, retrofit.
     * @param token,    token del web service.
     * @param idCourse, identificador del curso.
     */
    public NumberContentCourseRequest(Retrofit retrofit, String token, int idCourse) {
        this.retrofit = retrofit;
        this.token = token;
        this.idCourse = idCourse;
        this.cont = 0;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     *
     * @param params, params.
     * @return Integer, numero de herramientas externas.
     */
    @Override
    protected Integer doInBackground(Void... params) {
        MoodleService service = retrofit.create(MoodleService.class);
        Call<Content[]> call = service.getContentCourse(token, APIMoodle.GET_CONTENT_COURSE, APIMoodle.FORMAT_JSON, idCourse);

        try {
            Content[] content = call.execute().body();
            if (content != null)
                addContentCourse(content);
        } catch (IOException e) {
            Log.d("NumberContentCourse", "Request " + e.getMessage());
        }

        return cont;
    }

    /**
     * Metodo encargado de comprobar si es de tipo LTI.
     *
     * @param content
     */
    private void addContentCourse(Content[] content) {
        for (int i = 0; i < content.length; i++) {
            if (content[i] != null) {
                List<Module> modules = content[i].getModules();
                for (Module module : modules) {
                    if (module.getModname().equals("lti")) {
                        incNumberExternalTools();
                    }
                }
            }

        }
    }

    /**
     * Metodo encargado de incrementar el numero
     */
    private void incNumberExternalTools() {
        ++cont;
    }
}
