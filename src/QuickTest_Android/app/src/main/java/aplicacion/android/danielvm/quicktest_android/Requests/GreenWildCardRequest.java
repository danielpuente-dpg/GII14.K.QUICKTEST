package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.WildCard;
import aplicacion.android.danielvm.quicktest_android.Utils.RespuestaApiComodin;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 13/06/2017.
 */

public class GreenWildCardRequest extends AsyncTask<Void, Void, List<WildCard>> {
    private Retrofit retrofit;
    private int idQuestionnaire;

    public GreenWildCardRequest(Retrofit retrofit, int idQuestionnaire) {
        this.retrofit = retrofit;
        this.idQuestionnaire = idQuestionnaire;
    }

    @Override
    protected List<WildCard> doInBackground(Void... params) {
        List<WildCard> retorno = null;

        RestService service = retrofit.create(RestService.class);
        Call<RespuestaApiComodin> call = service.getGreenWildCard(idQuestionnaire);

        try {
            RespuestaApiComodin respuestaApiComodin = call.execute().body();
            retorno = respuestaApiComodin.getMensaje();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }
}
