package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

import com.google.gson.annotations.SerializedName;

/**
 * Clase WildCard encargada de encapsular el contenido de la respuesta JSON proporcionada
 * por el API Rest en un objeto Java.
 *
 * @autor Daniel Puente Gabarri.
 */

public class WildCard {
    @SerializedName("pregunta_idPregunta")
    private int idQuestion;
    @SerializedName("idRespuesta")
    private int idAnswer;

    public WildCard(int idQuestion, int idAnswer) {
        this.idQuestion = idQuestion;
        this.idAnswer = idAnswer;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }
}
