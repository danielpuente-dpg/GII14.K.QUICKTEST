package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by Daniel on 06/05/2017.
 */

public class TestRequest {

    private int idCuestionario;
    private List<Result> respuestas;

    public TestRequest(int idCuestionario, List<Result> respuestas) {
        this.idCuestionario = idCuestionario;
        this.respuestas = respuestas;
    }

    public int getIdCuestionario() {
        return idCuestionario;
    }

    public void setIdCuestionario(int idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    public List<Result> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Result> respuestas) {
        this.respuestas = respuestas;
    }

    public static Result parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Result result = gson.fromJson(response, Result.class);
        return result;
    }
}
