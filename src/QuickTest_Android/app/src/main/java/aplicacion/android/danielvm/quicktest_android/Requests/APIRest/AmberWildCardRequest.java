package aplicacion.android.danielvm.quicktest_android.Requests.APIRest;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.WildCard;
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
     * @return List<WildCard>, wildCards.
     */
    @Override
    protected List<WildCard> doInBackground(Void... params) {

        List<WildCard> retorno = null;

        RestService service = retrofit.create(RestService.class);
        Call<RespuestaApiComodin> call = service.getAmberWildCard(idQuestionnaire);

        try {
            RespuestaApiComodin respuestaApiComodin = call.execute().body();
            retorno = respuestaApiComodin.getMensaje();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }
}
