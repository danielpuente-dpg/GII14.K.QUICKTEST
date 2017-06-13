package aplicacion.android.danielvm.quicktest_android.Requests.APIRest;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Mensaje;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase ContentTestRequest encargada de proporcionar un cuestionario.
 *
 * @author Daniel Puente Gabarri.
 */

public class ContentTestRequest extends AsyncTask<Void, Void, List<Mensaje>> {
    private int idQuestionnaire;
    private Retrofit retrofit;

    /**
     * Constructor de la clase.
     *
     * @param retrofit,        retrofit.
     * @param idQuestionnaire, identificador del cuestionario.
     */
    public ContentTestRequest(Retrofit retrofit, int idQuestionnaire) {
        this.retrofit = retrofit;
        this.idQuestionnaire = idQuestionnaire;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     *
     * @param params, params.
     * @return List<Mensaje>, messages.
     */
    @Override
    protected List<Mensaje> doInBackground(Void... params) {
        RestService service = retrofit.create(RestService.class);
        Call<APIResponse> call = service.getTest(String.valueOf(idQuestionnaire));

        List<Mensaje> messages = null;
        try {
            messages = call.execute().body().getMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return messages;
    }
}
