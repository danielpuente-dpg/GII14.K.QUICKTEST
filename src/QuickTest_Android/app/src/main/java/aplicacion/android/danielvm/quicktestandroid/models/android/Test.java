package aplicacion.android.danielvm.quicktestandroid.models.android;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import aplicacion.android.danielvm.quicktestandroid.models.apirest.Answer;

/**
 * Clase Test encapsular el contenido de la respuesta JSON proporcionada
 * por el API Rest en un objeto Java.
 *
 * @author Daniel Puente Gabarri.
 */

public class Test {

    private String question;
    private List<Answer> answers;
    private int idQuestion;
    private String wildCard;

    public Test(String question, List<Answer> answers, int idQuestion) {
        this.question = question;
        this.answers = answers;
        this.idQuestion = idQuestion;
        this.wildCard = "";
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getWildCard() {
        return wildCard;
    }

    public void setWildCard(String wildCard) {
        this.wildCard = wildCard;
    }

    /**
     * Metodo encargado de realizar la conversion del contenido de la respuesta answer,
     * implicitamente invoca a la clase Answer y transforma el contenido a dicho objeto.
     * @param response, response
     * @return Answer, answer.
     */
    public static Answer parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Answer answer = gson.fromJson(response, Answer.class);
        return answer;
    }
}
