package aplicacion.android.danielvm.quicktestandroid.requests.apirest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import aplicacion.android.danielvm.quicktestandroid.api.APIServices.RestService;
import aplicacion.android.danielvm.quicktestandroid.models.apirest.WildCard;
import aplicacion.android.danielvm.quicktestandroid.models.apirest.WildcardApiResponse;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase AmberWildCardRequest encargada de obtener aquellas preguntas que tienen comodin ambar.
 *
 * @author Daniel Puente Gabarri.
 */

public class AmberWildCardRequest extends AsyncTask<Void, Void, List<WildCard>> {

    private Retrofit retrofit;
    private int idQuestionnaire;

    /**
     * Constructor de la clase.
     * @param retrofit, retrofit
     * @param idQuestionnaire, identificador del cuestionario.
     */
    public AmberWildCardRequest(Retrofit retrofit, int idQuestionnaire) {
        this.retrofit = retrofit;
        this.idQuestionnaire = idQuestionnaire;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     * @param params, params.
     * @return wildCards.
     */
    @Override
    protected List<WildCard> doInBackground(Void... params) {

        List<WildCard> retorno = null;

        RestService service = retrofit.create(RestService.class);
        Call<WildcardApiResponse> call = service.getAmberWildCard(idQuestionnaire);

        try {
            WildcardApiResponse wildcardApiResponse = call.execute().body();
            retorno = wildcardApiResponse.getWildCards();
        } catch (IOException e) {
            Log.d("AmberWildCardRequest", e.getMessage());
        }

        return retorno;
    }
}
