package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

import com.google.gson.annotations.SerializedName;

/**
 * Clase Answer encargada de encapsular la respuesta en formato JSON
 * del API Rest.
 *
 * @author Daniel Puente Gabarri.
 */
public class Answer {

    private int idRespuesta;
    @SerializedName("tit_Respuesta")
    private String titulo;
    @SerializedName("correcta")
    private int esCorrecta;
    @SerializedName("pregunta_idPregunta")
    private int idPregunta;
    private int contador;

    public Answer(int idRespuesta, String titulo, int esCorrecta, int idPregunta, int contador) {
        this.idRespuesta = idRespuesta;
        this.titulo = titulo;
        this.esCorrecta = esCorrecta;
        this.idPregunta = idPregunta;
        this.contador = contador;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(int esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

}
