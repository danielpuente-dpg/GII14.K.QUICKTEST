package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Clase Message encargada de encapsular la respuesta en formato JSON
 * del API Rest.
 *
 * @author Daniel Puente Gabarri.
 */

public class Message {
    @SerializedName("pregunta")
    private Question question;
    @SerializedName("respuesta")
    private List<Answer> answers;

    public Message(Question question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    /**
     * Metodo encargado de realizar la conversion del contenido de la respuesta answer,
     * implicitamente invoca a la clase Answer y transforma el contenido a dicho objeto.
     * @param response, response.
     * @return Answer, answer.
     */
    public static Answer parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Answer answer = gson.fromJson(response, Answer.class);
        return answer;
    }
}
