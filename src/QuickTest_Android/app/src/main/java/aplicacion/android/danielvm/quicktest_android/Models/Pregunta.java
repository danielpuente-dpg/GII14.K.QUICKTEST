package aplicacion.android.danielvm.quicktest_android.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel on 26/04/2017.
 */

public class Pregunta {
    private int idPregunta;
    @SerializedName("tituloPreg")
    private String titulo;
    @SerializedName("max_puntuacion")
    private int puntuacion;

    public Pregunta(){};

    public Pregunta(int idPregunta, String titulo, int puntuacion) {
        this.idPregunta = idPregunta;
        this.titulo = titulo;
        this.puntuacion = puntuacion;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
