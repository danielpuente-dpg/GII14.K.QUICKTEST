package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

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
 * Created by Daniel on 09/06/2017.
 */

public class CoreUserGetCourseUserRequest extends AsyncTask<Void, Void, List<Role>> {

    private Retrofit retrofit;
    private String token;
    private int idUser;
    private int idCourse;

    public CoreUserGetCourseUserRequest(Retrofit retrofit, String token, int idUser, int idCourse) {
        this.retrofit = retrofit;
        this.token = token;
        this.idUser = idUser;
        this.idCourse = idCourse;
    }

    @Override
    protected List<Role> doInBackground(Void... params) {
        List<Role> roles = null;
        MoodleService service = retrofit.create(MoodleService.class);
        Call<UserProfilesByCourse[]> call = service.getUserGetCourse(token, APIMoodle.GET_PROFILES_USER_BY_USER, APIMoodle.FORMAT_JSON, idUser, idCourse);

        try {
            roles = call.execute().body()[0].getRoles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return roles;
    }
}
