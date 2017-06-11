package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.APIResponse;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 11/06/2017.
 */

public class UserGradeRequest extends AsyncTask<Void, Void, Double> {

    private Retrofit retrofit;
    private int idCuestionario;
    private String idAlumno;

    public UserGradeRequest(Retrofit retrofit, int idCuestionario, String idAlumno) {
        this.retrofit = retrofit;
        this.idCuestionario = idCuestionario;
        this.idAlumno = idAlumno;
    }

    @Override
    protected Double doInBackground(Void... params) {

        RestService service = retrofit.create(RestService.class);
        Call<APIResponse> call = service.getGrade(idAlumno, idCuestionario);

        double grade = 0;
        try {
            APIResponse apiResponse = call.execute().body();
            grade = Double.parseDouble(apiResponse.getMensaje());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return grade;
    }
}
