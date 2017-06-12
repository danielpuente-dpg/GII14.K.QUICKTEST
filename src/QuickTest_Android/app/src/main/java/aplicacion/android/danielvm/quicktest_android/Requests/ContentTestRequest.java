package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Mensaje;
import aplicacion.android.danielvm.quicktest_android.Utils.RespuestaApi;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 12/06/2017.
 */

public class ContentTestRequest extends AsyncTask<Void, Void, List<Mensaje>> {
    private int idCuestionario;
    private Retrofit retrofit;

    public ContentTestRequest(Retrofit retrofit, int idCuestionario) {
        this.retrofit = retrofit;
        this.idCuestionario = idCuestionario;
    }

    @Override
    protected List<Mensaje> doInBackground(Void... params) {
        RestService service = retrofit.create(RestService.class);
        Call<RespuestaApi> call = service.getTest(String.valueOf(idCuestionario));

        List<Mensaje> messages = null;
        try {
            messages = call.execute().body().getMensaje();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return messages;
    }
}
