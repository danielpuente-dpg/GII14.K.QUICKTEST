package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 08/06/2017.
 */

public class CourseRequest extends AsyncTask<Void, Void, List<Course>> {
    private Retrofit retrofit;
    private String token;

    public CourseRequest(Retrofit retrofit, String token) {
        this.retrofit = retrofit;
        this.token = token;
    }

    @Override
    protected List<Course> doInBackground(Void... params) {
        List<Course> courses = null;
        MoodleService service = retrofit.create(MoodleService.class);
        Call<List<Course>> call = service.getCourses(token, APIMoodle.GET_COURSES, APIMoodle.FORMAT_JSON);

        try {
            courses = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }
}
