package aplicacion.android.danielvm.quicktestandroid.requests.apirest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import aplicacion.android.danielvm.quicktestandroid.api.APIServices.RestService;
import aplicacion.android.danielvm.quicktestandroid.models.apirest.SingleApiResponse;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase StatusQuestionnaireRequest encargada de obtener el estado de un cuestionario.
 *
 * @author Daniel Puente Gabarri.
 */

public class StatusQuestionnaireRequest extends AsyncTask<Void, Void, Integer> {
    private Retrofit retrofit;
    private String oauth_consumer_key;
    private int idQuestionnaire;

    /**
     * Constructor de la clase.
     *
     * @param retrofit,           retrofit.
     * @param oauth_consumer_key, clave del usuario.
     * @param idQuestionnaire,    identificador del usuario.
     */
    public StatusQuestionnaireRequest(Retrofit retrofit, String oauth_consumer_key, int idQuestionnaire) {
        this.retrofit = retrofit;
        this.oauth_consumer_key = oauth_consumer_key;
        this.idQuestionnaire = idQuestionnaire;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     *
     * @param params, params.
     * @return Integer, estado.
     */
    @Override
    protected Integer doInBackground(Void... params) {
        int status = -1;

        RestService service = retrofit.create(RestService.class);
        Call<SingleApiResponse> call = service.getStatusTest(oauth_consumer_key, idQuestionnaire);

        try {
            status = Integer.parseInt(call.execute().body().getMessage());
            return status;
        } catch (IOException e) {
            Log.d("StatusQuestionnaire", "Request: " + e.getMessage());
        }


        return status;
    }
}
