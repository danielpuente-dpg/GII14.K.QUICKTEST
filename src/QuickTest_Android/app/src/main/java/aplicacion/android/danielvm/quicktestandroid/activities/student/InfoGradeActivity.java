package aplicacion.android.danielvm.quicktestandroid.activities.student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import aplicacion.android.danielvm.quicktestandroid.models.apirest.FeedBack;
import aplicacion.android.danielvm.quicktestandroid.R;

/**
 * Clase InfoGradeActivity encargada de mostrar mas informacion sobre el cuestionario resuelto
 * a los alumnos.
 *
 * @author Daniel Puente Gabarri.
 */
public class InfoGradeActivity extends AppCompatActivity {

    // Elementos de la UI
    // Primera fila
    private TextView textViewNumberQuestions;
    private TextView textViewNumberQuestionsTrue;
    private TextView textViewNumberQuestionsError;
    private TextView textViewGrade;

    // Segunda fila
    private TextView textViewNumberQuestionsGreen;
    private TextView textViewNumberQuestionsTrueGreen;
    private TextView textViewNumberQuestionsErrorGreen;
    private TextView textViewGradeGreen;

    // Tercera fila
    private TextView textViewNumberQuestionsAmber;
    private TextView textViewNumberQuestionsTrueAmber;
    private TextView textViewNumberQuestionsErrorAmber;
    private TextView textViewGradeAmber;

    // Cuarta fila
    private TextView textViewStatus;
    private TextView textViewGradeStatus;

    // Quinta fila
    private TextView textViewNumberAnswerQuestions;
    private TextView textViewNumberAnswerQuestionsTrue;
    private TextView textViewNumberAnswerQuestionsError;
    private TextView textViewFinalGrade;

    private LinearLayout linearLayoutColorAIG;
    private TextView textViewClockMessageAIG;
    private TextView textViewPlusClockAIG;

    // Atributos
    private static final String FAST = "MUY RÁPIDO";
    private static final String SLOW = "LENTO";
    private GradeActivity activity = new GradeActivity();
    private FeedBack feedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_grade);

        // Incluimos el toolbar
        enforceBar();

        // Obtenemos la info del FeedBack
        feedBack = activity.getFeedBack();

        // Instanciamos los elementos de la UI
        bindUI();

        // Actualizamos la info
        setDataView();
    }

    /**
     * Metodo encargado de instanciar los elementos de la UI.
     */
    private void bindUI() {
        // Primera fila
        textViewNumberQuestions = (TextView) findViewById(R.id.textViewNumberQuestions);
        textViewNumberQuestionsTrue = (TextView) findViewById(R.id.textViewNumberQuestionsTrue);
        textViewNumberQuestionsError = (TextView) findViewById(R.id.textViewNumberQuestionsError);
        textViewGrade = (TextView) findViewById(R.id.textViewGrade);

        // Segunda fila

        textViewNumberQuestionsGreen = (TextView) findViewById(R.id.textViewNumberQuestionsGreen);
        textViewNumberQuestionsTrueGreen = (TextView) findViewById(R.id.textViewNumberQuestionsTrueGreen);
        textViewNumberQuestionsErrorGreen = (TextView) findViewById(R.id.textViewNumberQuestionsErrorGreen);
        textViewGradeGreen = (TextView) findViewById(R.id.textViewGradeGreen);

        // Tercera fila

        textViewNumberQuestionsAmber = (TextView) findViewById(R.id.textViewNumberQuestionsAmber);
        textViewNumberQuestionsTrueAmber = (TextView) findViewById(R.id.textViewNumberQuestionsTrueAmber);
        textViewNumberQuestionsErrorAmber = (TextView) findViewById(R.id.textViewNumberQuestionsErrorAmber);
        textViewGradeAmber = (TextView) findViewById(R.id.textViewGradeAmber);

        // Cuarta fila

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        textViewGradeStatus = (TextView) findViewById(R.id.textViewGradeStatus);

        // Quinta fila

        textViewNumberAnswerQuestions = (TextView) findViewById(R.id.textViewNumberAnswerQuestions);
        textViewNumberAnswerQuestionsTrue = (TextView) findViewById(R.id.textViewNumberAnswerQuestionsTrue);
        textViewNumberAnswerQuestionsError = (TextView) findViewById(R.id.textViewNumberAnswerQuestionsError);
        textViewFinalGrade = (TextView) findViewById(R.id.textViewFinalGrade);

        // Cuadro

        linearLayoutColorAIG = (LinearLayout) findViewById(R.id.linearLayoutColorAIG);
        textViewClockMessageAIG = (TextView) findViewById(R.id.textViewClockMessageAIG);
        textViewPlusClockAIG = (TextView) findViewById(R.id.textViewPlusClockAIG);
    }

    /**
     * Metodo encargado de actualizar la informacion en los elementos de la UI.
     */
    private void setDataView() {
        // Primera fila
        textViewNumberQuestions.setText(String.valueOf(feedBack.getcSin()));
        textViewNumberQuestionsTrue.setText(String.valueOf(feedBack.getAciertoSin()));
        textViewNumberQuestionsError.setText(String.valueOf(feedBack.getFallosSin()));
        textViewGrade.setText(feedBack.getPuntSin());

        // Segunda fila
        textViewNumberQuestionsGreen.setText(String.valueOf(feedBack.getcVerde()));
        textViewNumberQuestionsTrueGreen.setText(String.valueOf(feedBack.getAciertoVerde()));
        textViewNumberQuestionsErrorGreen.setText(String.valueOf(feedBack.getFallosVerde()));
        textViewGradeGreen.setText(feedBack.getPuntVerde());

        // Tercera fila
        textViewNumberQuestionsAmber.setText(String.valueOf(feedBack.getcAmarillo()));
        textViewNumberQuestionsTrueAmber.setText(String.valueOf(feedBack.getAciertoAmarillo()));
        textViewNumberQuestionsErrorAmber.setText(String.valueOf(feedBack.getFallosAmarillo()));
        textViewGradeAmber.setText(feedBack.getPuntAmarillo());

        // Cuarta fila
        textViewStatus.setText(feedBack.getOrdenRespuesta());
        textViewGradeStatus.setText(feedBack.getPremio());

        // Quinta fila
        String total = String.valueOf(feedBack.getcPreguntasRespondidas()) + "/" + String.valueOf(feedBack.getTotalPreguntas());
        textViewNumberAnswerQuestions.setText(total);
        int aciertos = feedBack.getAciertoSin() + feedBack.getAciertoVerde() + feedBack.getAciertoAmarillo();
        textViewNumberAnswerQuestionsTrue.setText(String.valueOf(aciertos));
        int fallos = feedBack.getFallosSin() + feedBack.getFallosVerde() + feedBack.getFallosAmarillo();
        textViewNumberAnswerQuestionsError.setText(String.valueOf(fallos));
        total = String.valueOf(feedBack.getPuntTotal()) + "/" + String.valueOf(feedBack.getPuntMaxPosible());
        textViewFinalGrade.setText(total);

        // Cuadro

        if (feedBack.getOrdenRespuesta().equals(FAST)) {
            linearLayoutColorAIG.setBackgroundResource(R.color.colorFast);
        } else if (feedBack.getOrdenRespuesta().equals(SLOW)) {
            linearLayoutColorAIG.setBackgroundResource(R.color.colorSlow);
        } else {
            linearLayoutColorAIG.setBackgroundResource(R.color.colorAmberWildCard);
        }

        textViewClockMessageAIG.setText(feedBack.getMensaje());
        textViewPlusClockAIG.setText(feedBack.getPremio() + " PUNTOS");
    }

    /**
     * Metodo encargado de forzar la carga del action bar.
     */
    private void enforceBar() {
        // Forzar y cargar iconos
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String cuestionario = activity.getNameQuestionnaire();
        getSupportActionBar().setTitle(cuestionario);
    }
}
