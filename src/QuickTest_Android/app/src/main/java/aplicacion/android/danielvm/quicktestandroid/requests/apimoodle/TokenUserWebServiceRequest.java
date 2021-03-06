package aplicacion.android.danielvm.quicktestandroid.requests.apimoodle;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import aplicacion.android.danielvm.quicktestandroid.api.APIMoodle;
import aplicacion.android.danielvm.quicktestandroid.api.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktestandroid.models.moodle.Token;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase TokenUserWebServiceRequest encargada de proporcionar el token del usuario.
 *
 * @author Daniel Puente Gabarri.
 */

public class TokenUserWebServiceRequest extends AsyncTask<Void, Void, Token> {
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
    protected Token doInBackground(Void... params) {
        Token token = null;

        MoodleService service = retrofit.create(MoodleService.class);
        Call<Token> call = service.getToken(username, password, APIMoodle.APP);


        try {
            token = call.execute().body();
        } catch (IOException e) {
            Log.d("TokenUserWebService", "Request: " +  e.getMessage());
        }


        return token;
    }
}
