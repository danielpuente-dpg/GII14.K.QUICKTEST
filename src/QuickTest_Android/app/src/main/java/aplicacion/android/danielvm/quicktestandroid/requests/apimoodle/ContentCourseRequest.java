package aplicacion.android.danielvm.quicktestandroid.requests.apimoodle;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aplicacion.android.danielvm.quicktestandroid.api.APIMoodle;
import aplicacion.android.danielvm.quicktestandroid.api.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktestandroid.models.moodle.Content;
import aplicacion.android.danielvm.quicktestandroid.models.moodle.Module;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase ContentCourseRequest encargada de proporcionar el contenido de un curso.
 *
 * @author Daniel Puente Gabarri.
 */

public class ContentCourseRequest extends AsyncTask<Void, Void, List<Module>> {

    private Retrofit retrofit;
    private int idCourse;
    private String token;
    private List<Module> modules;

    /**
     * Constructor de la clase.
     * @param retrofit, retrofit
     * @param idCourse, identificador del curso.
     * @param token, token del web service.
     */
    public ContentCourseRequest(Retrofit retrofit, int idCourse, String token) {
        this.retrofit = retrofit;
        this.idCourse = idCourse;
        this.token = token;
        modules = new ArrayList<>();
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     * @param params, params.
     * @return modules.
     */
    @Override
    protected List<Module> doInBackground(Void... params) {
        MoodleService service = retrofit.create(MoodleService.class);
        Call<Content[]> call = service.getContentCourse(token, APIMoodle.GET_CONTENT_COURSE, APIMoodle.FORMAT_JSON, idCourse);

        try {
            Content[] content = call.execute().body();
            if (content != null)
                addContentCourse(content);
        } catch (IOException e) {
            Log.d("ContentCourseRequest", e.getMessage());
        }

        return modules;
    }

    /**
     * Metodo encargado de añadir el contenido si es de tipo LTI.
     * @param content, content.
     */
    private void addContentCourse(Content[] content) {
        for (int i = 0; i < content.length; i++) {
            if (content[i] != null) {
                List<Module> modules = content[i].getModules();
                for (Module module : modules) {
                    if (module.getModname().equals("lti")) {
                        addExternalTool(module);
                    }
                }
            }

        }
    }

    /**
     * Metodo que añade un module dado
     * @param module, module.
     */
    private void addExternalTool(Module module) {
        modules.add(module);
    }
}
