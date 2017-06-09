package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Content;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Module;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 08/06/2017.
 */

public class NumberContentCourseRequest extends AsyncTask<Void, Void, Integer> {

    private Retrofit retrofit;
    private String token;
    private int idCourse;
    private int cont;

    public NumberContentCourseRequest(Retrofit retrofit, String token, int idCourse) {
        this.retrofit = retrofit;
        this.token = token;
        this.idCourse = idCourse;
        this.cont = 0;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        MoodleService service = retrofit.create(MoodleService.class);
        Call<Content[]> call = service.getContentCourse(token, APIMoodle.GET_CONTENT_COURSE, APIMoodle.FORMAT_JSON, idCourse);

        try {
            Content[] content = call.execute().body();
            if (content != null)
                addContentCourse(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cont;
    }

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

    private void incNumberExternalTools() {
        ++cont;
    }
}
