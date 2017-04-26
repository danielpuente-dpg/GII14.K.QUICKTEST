package aplicacion.android.danielvm.quicktest_android.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by Daniel on 23/04/2017.
 */

public class Test {

    private String pregunta;
    private List<Respuesta> respuesta;

    public Test() {
    }

    public Test(String pregunta, List<Respuesta> respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
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

    public static Respuesta parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Respuesta respuesta = gson.fromJson(response, Respuesta.class);
        return respuesta;
    }
}
