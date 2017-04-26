package aplicacion.android.danielvm.quicktest_android.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Daniel on 26/04/2017.
 */

public class Mensaje {
    private Pregunta pregunta;
    @SerializedName("respuesta")
    private List<Respuesta> respuestas;

    public Mensaje() {
    }

    public Mensaje(Pregunta pregunta, List<Respuesta> respuestas) {
        this.pregunta = pregunta;
        this.respuestas = respuestas;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public static Respuesta parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Respuesta respuesta = gson.fromJson(response, Respuesta.class);
        return respuesta;
    }
}
