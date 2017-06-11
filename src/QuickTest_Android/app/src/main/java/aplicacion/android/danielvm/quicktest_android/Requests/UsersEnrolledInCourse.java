package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.UserEnrol;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 11/06/2017.
 */

public class UsersEnrolledInCourse extends AsyncTask<Void, Void, UserEnrol[]> {

    private Retrofit retrofit;
    private String token;
    private int idCourse;

    public UsersEnrolledInCourse(Retrofit retrofit, String token, int idCourse) {
        this.retrofit = retrofit;
        this.token = token;
        this.idCourse = idCourse;
    }

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
