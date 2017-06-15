package aplicacion.android.danielvm.quicktestandroid.activities.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktestandroid.api.APIRest;
import aplicacion.android.danielvm.quicktestandroid.models.apirest.FeedBack;
import aplicacion.android.danielvm.quicktestandroid.R;
import aplicacion.android.danielvm.quicktestandroid.requests.apirest.UserGradeRequest;
import aplicacion.android.danielvm.quicktestandroid.requests.apirest.UserInfoGradeRequest;

/**
 * Clase GradeActivity encarga de proporcionar informacion sobre el resultado obtenido
 * en el cuestionario.
 *
 * @author Daniel Puente Gabarri.
 */
public class GradeActivity extends AppCompatActivity {

    private static final String TAG = "GradeActivity";
    // Elemento de la UI
    private TextView textViewGrade;
    private TextView textViewClockMessage;
    private TextView textViewPlusClock;
    private Button buttonViewResult;
    private LinearLayout linearLayoutColor;

    // Atributos
    private double grade;
    private String idStudent;
    private int idQuestionnaire;
    private static String nameQuestionnaire;
    private static FeedBack feedBack;
    private static final String FAST = "MUY RÁPIDO";
    private static final String SLOW = "LENTO";
    private static final int NOT_AVAILABLE = -1;
    private StudentActivity activity = new StudentActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        // Recuperamos la info
        getDataBundle();
        enforceBar();
        getInfo();

        // Instanciamos los elementos de UI.
        bindUI();

        // Modificamos la informacion
        setData();

        // Evento de ver mas resultados
        buttonViewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInfoGradeActivity();
            }
        });

    }

    /**
     * Metodo encargado de formatear la presentacion de los datos en funcion
     * delos datos obtenidos.
     */
    private void setData() {
        // Si la calificacion no esta disponible desde la app
        if (grade == NOT_AVAILABLE) {
            textViewGrade.setText("La calificación de este cuestionario no se encuentra disponible desde la app movil.");
        } else {
            grade *= 10;
            textViewGrade.setText("Su calificación final en este cuestionario es " + grade + "/10");
        }

        textViewClockMessage.setText(feedBack.getMensaje());
        textViewPlusClock.setText(feedBack.getPremio() + " PUNTOS");

        if (feedBack.getOrdenRespuesta().equals(FAST)) {
            linearLayoutColor.setBackgroundResource(R.color.colorFast);
        } else if (feedBack.getOrdenRespuesta().equals(SLOW)) {
            linearLayoutColor.setBackgroundResource(R.color.colorSlow);
        } else {
            linearLayoutColor.setBackgroundResource(R.color.colorAmberWildCard);
        }
    }

    /**
     * Metodo encargado de instanciar los elementos de la UI.
     */
    private void bindUI() {
        textViewGrade = (TextView) findViewById(R.id.textViewTextGrade);
        textViewClockMessage = (TextView) findViewById(R.id.textViewClockMessage);
        textViewPlusClock = (TextView) findViewById(R.id.textViewPlusClock);
        buttonViewResult = (Button) findViewById(R.id.buttonViewResults);
        linearLayoutColor = (LinearLayout) findViewById(R.id.linearLayoutColor);
    }

    /**
     * Metodo encargado de direccionar a InfoGradeActivity.
     */
    private void goToInfoGradeActivity() {
        Intent intent = new Intent(this, InfoGradeActivity.class);
        intent.putExtra("idCuestionario", idQuestionnaire);
        intent.putExtra("idAlumno", idStudent);
        startActivity(intent);
    }

    /**
     * Metodo encargado de forzar la carga del action bar.
     */
    private void enforceBar() {
        // Forzar y cargar iconos
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Calificación");
    }

    /**
     * Metodo Recuperamos los datos del activity de origen.
     */
    private void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d(TAG, "Intent was null");
        else {
            Log.d(TAG, "Intent OK");
            idQuestionnaire = bundle.getInt("idQuestionnaire");
            idStudent = bundle.getString("idStudent");
            nameQuestionnaire = bundle.getString("nameQuestionnaire");
        }
    }

    /**
     * Metodo encargado de obtener otra informacion necesaria.
     */
    public void getInfo() {
        grade = getGrade();
        feedBack = getInfoFeedBack();
    }

    /**
     * Metodo encargado de recuperar la nota del cuestionario, resuelto desde la app.
     *
     * @return Double, grade.
     */
    private Double getGrade() {
        Double retorno = null;
        idStudent += ":" + activity.getUser().getId();
        UserGradeRequest userGradeRequest = new UserGradeRequest(APIRest.getApi(), idQuestionnaire, idStudent);
        try {
            retorno = userGradeRequest.execute().get();
        } catch (InterruptedException e) {
            Log.d(TAG, e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            Log.d(TAG, e.getMessage());
            Thread.currentThread().interrupt();
        }
        return retorno;
    }

    /**
     * Metodo encargado de obtener la informacion de la tabla resumen.
     *
     * @return FeedBack, retorno.
     */
    private FeedBack getInfoFeedBack() {
        FeedBack retorno = null;
        UserInfoGradeRequest userInfoGradeRequest = new UserInfoGradeRequest(APIRest.getApi(), idQuestionnaire, idStudent);
        try {
            retorno = userInfoGradeRequest.execute().get();
        } catch (InterruptedException e) {
            Log.d(TAG, e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            Log.d(TAG, e.getMessage());
            Thread.currentThread().interrupt();
        }
        return retorno;
    }

    /**
     * Metodo encargado de obtener la informacion de la tabla.
     * @return FeedBack, feedBack;
     */
    public FeedBack getFeedBack(){
        return feedBack;
    }

    /**
     * Metodo encargado de proporcionar el nombre del cuestionario.
     * @return
     */
    public String getNameQuestionnaire(){
        return nameQuestionnaire;
    }
}
