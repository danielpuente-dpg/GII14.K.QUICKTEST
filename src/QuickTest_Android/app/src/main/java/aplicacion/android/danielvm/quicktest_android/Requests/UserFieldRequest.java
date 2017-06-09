package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.User;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 08/06/2017.
 */

public class UserFieldRequest extends AsyncTask<Void, Void, User> {
    private Retrofit retrofit;
    private String token;
    private String name;

    public UserFieldRequest(Retrofit retrofit, String token, String name) {
        this.retrofit = retrofit;
        this.token = token;
        this.name = name;
    }

    @Override
    protected User doInBackground(Void... params) {
        User user = null;
        MoodleService service = retrofit.create(MoodleService.class);
        Call<User[]> call = service.getUserByField(token, APIMoodle.GET_USER_BY_FIELD, APIMoodle.FORMAT_JSON, "username", name);

        try {
            user = call.execute().body()[0];
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }
}
