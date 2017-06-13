package aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Token;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.User;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase TokenUserWebServiceRequest encargada de proporcionar el token del usuario.
 *
 * @author Daniel Puente Gabarri.
 */

public class TokenUserWebServiceRequest extends AsyncTask<Void, Void, String> {
    private Retrofit retrofit;
    private String username;
    private String password;

    /**
     * Constructor de la clase.
     * @param retrofit, retrofit.
     * @param username, nombre del usuario.
     * @param password, contraseña del usuario.
     */
    public TokenUserWebServiceRequest(Retrofit retrofit, String username, String password) {
        this.retrofit = retrofit;
        this.username = username;
        this.password = password;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     * @param params, params.
     * @return String, token.
     */
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
