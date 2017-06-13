package aplicacion.android.danielvm.quicktest_android.Requests.APIRest;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.FeedBack;
import aplicacion.android.danielvm.quicktest_android.Requests.APIRest.RespuestaApiFeedback;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 13/06/2017.
 */

public class UserInfoGradeRequest extends AsyncTask<Void, Void, FeedBack> {

    private Retrofit retrofit;
    private int idCuestionario;
    private String idAlumno;

    public UserInfoGradeRequest(Retrofit retrofit, int idCuestionario, String idAlumno) {
        this.retrofit = retrofit;
        this.idCuestionario = idCuestionario;
        this.idAlumno = idAlumno;
    }

    @Override
    protected FeedBack doInBackground(Void... params) {
        FeedBack feedBack = null;

        RestService service = retrofit.create(RestService.class);
        Call<RespuestaApiFeedback> call = service.getFeedBack(idCuestionario, idAlumno);

        try {
            RespuestaApiFeedback respuestaApiFeedback = call.execute().body();
            feedBack = respuestaApiFeedback.getMensaje();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return feedBack;
    }
}
