package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Utils.SingleRespuestaAPI;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 09/06/2017.
 */

public class StatusQuestionaryRequest extends AsyncTask<Void, Void, Integer> {
    private Retrofit retrofit;
    private String oauth_consumer_key;
    private int idCuestionario;

    public StatusQuestionaryRequest(Retrofit retrofit, String oauth_consumer_key, int idCuestionario) {
        this.retrofit = retrofit;
        this.oauth_consumer_key = oauth_consumer_key;
        this.idCuestionario = idCuestionario;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        int estado = -1;

        RestService service = retrofit.create(RestService.class);
        Call<SingleRespuestaAPI> call = service.getStatusTest(oauth_consumer_key, idCuestionario);

        try {
            estado = Integer.parseInt(call.execute().body().getMensaje());
            return estado;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return estado;
    }
}
