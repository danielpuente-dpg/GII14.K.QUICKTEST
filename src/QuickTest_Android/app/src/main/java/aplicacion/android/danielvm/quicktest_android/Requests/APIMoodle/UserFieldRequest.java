package aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.User;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase UserFieldRequest encargada de proporcionar la informacion de un usuario.
 *
 * @author Daniel Puente Gabarri.
 */

public class UserFieldRequest extends AsyncTask<Void, Void, User> {
    private Retrofit retrofit;
    private String token;
    private String name;

    /**
     * Constructor de la clase.
     *
     * @param retrofit, retrofit.
     * @param token,    token del web service.
     * @param name,     nombre del usuario.
     */
    public UserFieldRequest(Retrofit retrofit, String token, String name) {
        this.retrofit = retrofit;
        this.token = token;
        this.name = name;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     *
     * @param params
     * @return
     */
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
