package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Token;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.User;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 08/06/2017.
 */

public class TokenUserWebServiceRequest extends AsyncTask<Void, Void, String> {
    private Retrofit retrofit;
    private String username;
    private String password;

    public TokenUserWebServiceRequest(Retrofit retrofit, String username, String password) {
        this.retrofit = retrofit;
        this.username = username;
        this.password = password;
    }

    @Override
    protected String doInBackground(Void... params) {
        Token token = null;

        MoodleService service = retrofit.create(MoodleService.class);
        Call<Token> call = service.getToken(username, password, APIMoodle.APP);


        try {
            token = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return token.getToken();
    }
}
