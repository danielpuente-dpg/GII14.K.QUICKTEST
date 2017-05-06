package aplicacion.android.danielvm.quicktest_android.Models.Android;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Respuesta;

/**
 * Created by Daniel on 23/04/2017.
 */

public class Test {

    private String pregunta;
    private List<Respuesta> respuesta;
    private int idPregunta;


    public Test() {
    }

    public Test(String pregunta, List<Respuesta> respuesta, int idPregunta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.idPregunta = idPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public List<Respuesta> getRespuestas() {
        return respuesta;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuesta = respuestas;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public static Respuesta parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Respuesta respuesta = gson.fromJson(response, Respuesta.class);
        return respuesta;
    }
}
