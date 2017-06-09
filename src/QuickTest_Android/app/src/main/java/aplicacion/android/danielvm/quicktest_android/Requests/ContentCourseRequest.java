package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
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

public class ContentCourseRequest extends AsyncTask<Void, Void, List<Module>> {

    private Retrofit retrofit;
    private int idCourse;
    private String token;
    private List<Module> modules;

    public ContentCourseRequest(Retrofit retrofit, int idCourse, String token) {
        this.retrofit = retrofit;
        this.idCourse = idCourse;
        this.token = token;
        modules = new ArrayList<>();
    }

    @Override
    protected List<Module> doInBackground(Void... params) {
        MoodleService service = retrofit.create(MoodleService.class);
        Call<Content[]> call = service.getContentCourse(token, APIMoodle.GET_CONTENT_COURSE, APIMoodle.FORMAT_JSON, idCourse);

        try {
            Content[] content = call.execute().body();
            if (content != null)
                addContentCourse(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modules;
    }

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

    private void addExternalTool(Module module) {
        modules.add(module);
    }
}
