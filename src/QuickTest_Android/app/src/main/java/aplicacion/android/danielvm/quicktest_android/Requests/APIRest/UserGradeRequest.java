package aplicacion.android.danielvm.quicktest_android.Requests.APIRest;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.APIResponse;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase UserGradeRequest encargada de obtener la calificacion de un cuestionario
 * resuelto desde la app.
 *
 * @author Daniel Puente Gabarri.
 */

public class UserGradeRequest extends AsyncTask<Void, Void, Double> {

    private Retrofit retrofit;
    private int idQuestionnaire;
    private String idStudent;

    /**
     * Constructor de la clase.
     *
     * @param retrofit,        retrofit.
     * @param idQuestionnaire, identificador del cuestionionario.
     * @param idStudent,       identificador del alumno.
     */
    public UserGradeRequest(Retrofit retrofit, int idQuestionnaire, String idStudent) {
        this.retrofit = retrofit;
        this.idQuestionnaire = idQuestionnaire;
        this.idStudent = idStudent;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     *
     * @param params, params.
     * @return Double, grade.
     */
    @Override
    protected Double doInBackground(Void... params) {

        RestService service = retrofit.create(RestService.class);
        Call<APIResponse> call = service.getGrade(idStudent, idQuestionnaire);

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
