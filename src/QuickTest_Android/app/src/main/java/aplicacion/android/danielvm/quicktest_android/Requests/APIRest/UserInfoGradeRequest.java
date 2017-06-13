package aplicacion.android.danielvm.quicktest_android.Requests.APIRest;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.FeedBack;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase UserInfoGradeRequest encargada de obtener la informacion sobre un cuestionario
 * resuelto.
 *
 * @author Daniel Puente Gabarri.
 */

public class UserInfoGradeRequest extends AsyncTask<Void, Void, FeedBack> {

    private Retrofit retrofit;
    private int idQuestionnaire;
    private String idStudent;

    /**
     * Constructor de la clase.
     *
     * @param retrofit,        retrofit.
     * @param idQuestionnaire, identificador de cuestionario.
     * @param idStudent,       identificador del alumno.
     */
    public UserInfoGradeRequest(Retrofit retrofit, int idQuestionnaire, String idStudent) {
        this.retrofit = retrofit;
        this.idQuestionnaire = idQuestionnaire;
        this.idStudent = idStudent;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     *
     * @param params, params.
     * @return FeedBack, feedBack.
     */
    @Override
    protected FeedBack doInBackground(Void... params) {
        FeedBack feedBack = null;

        RestService service = retrofit.create(RestService.class);
        Call<FeedbackApiResponse> call = service.getFeedBack(idQuestionnaire, idStudent);

        try {
            FeedbackApiResponse feedbackApiResponse = call.execute().body();
            feedBack = feedbackApiResponse.getFeedBack();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return feedBack;
    }
}
