package aplicacion.android.danielvm.quicktestandroid.requests.apirest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import aplicacion.android.danielvm.quicktestandroid.api.APIServices.RestService;
import aplicacion.android.danielvm.quicktestandroid.models.apirest.SingleApiResponse;
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
        Call<SingleApiResponse> call = service.getGrade(idStudent, idQuestionnaire);

        double grade = 0;
        try {
            SingleApiResponse singleApiResponse = call.execute().body();
            grade = Double.parseDouble(singleApiResponse.getMessage());
        } catch (IOException e) {
            Log.d("UserGradeRequest", e.getMessage());
        }


        return grade;
    }
}
