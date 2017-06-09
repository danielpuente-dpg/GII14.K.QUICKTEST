package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 08/06/2017.
 */

public class EnrolUserCourseRequest extends AsyncTask<Void, Void, Course[]> {
    private Retrofit retrofit;
    private String token;
    private int userId;

    public EnrolUserCourseRequest(Retrofit retrofit, String token, int userId) {
        this.retrofit = retrofit;
        this.token = token;
        this.userId = userId;
    }

    @Override
    protected Course[] doInBackground(Void... params) {
        Course[] course = null;

        MoodleService service = retrofit.create(MoodleService.class);
        Call<Course[]> call = service.getCoursesByUserId(token, APIMoodle.GET_COURSES_BY_USER_ID, APIMoodle.FORMAT_JSON, userId);

        try {
            course = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return course;
    }
}
