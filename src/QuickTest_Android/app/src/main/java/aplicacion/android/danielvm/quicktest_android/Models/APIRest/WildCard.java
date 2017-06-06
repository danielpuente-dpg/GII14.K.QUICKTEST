package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

/**
 * Created by Daniel on 06/06/2017.
 */

public class WildCard {
    private int pregunta_idPregunta;
    private int idRespuesta;

    public WildCard(int pregunta_idPregunta, int idRespuesta) {
        this.pregunta_idPregunta = pregunta_idPregunta;
        this.idRespuesta = idRespuesta;
    }

    public int getPregunta_idPregunta() {
        return pregunta_idPregunta;
    }

    public void setPregunta_idPregunta(int pregunta_idPregunta) {
        this.pregunta_idPregunta = pregunta_idPregunta;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }
}
