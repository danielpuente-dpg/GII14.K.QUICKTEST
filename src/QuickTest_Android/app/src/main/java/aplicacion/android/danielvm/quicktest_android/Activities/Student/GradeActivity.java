package aplicacion.android.danielvm.quicktest_android.Activities.Student;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIRest;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.APIResponse;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.FeedBack;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Utils.RespuestaApiFeedback;
import retrofit2.Call;
import retrofit2.Retrofit;

public class GradeActivity extends AppCompatActivity {

    private TextView textViewGrade;
    private TextView textViewClockMessage;
    private TextView textViewPlusClock;
    private Button buttonViewResult;
    private LinearLayout linearLayoutColor;

    private double grade;
    private String idAlumno;
    private int idCuestionario;
    public static String cuestionario;

    public static FeedBack feedBack;

    private static final String FAST = "MUY RÁPIDO";
    private static final String SLOW = "LENTO";

    private static final int NOT_AVAILABLE = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        // Recuperamos la info
        getDataBundle();
        enforceBar();
        getInfo();

        textViewGrade = (TextView) findViewById(R.id.textViewTextGrade);
        textViewClockMessage = (TextView) findViewById(R.id.textViewClockMessage);
        textViewPlusClock = (TextView) findViewById(R.id.textViewPlusClock);
        buttonViewResult = (Button) findViewById(R.id.buttonViewResults);
        linearLayoutColor = (LinearLayout) findViewById(R.id.linearLayoutColor);


        // Evento de Ver mas resultados
        buttonViewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInfoGradeActivity();
            }
        });


        // Logica de los setters
        // Obtenemos la informacion

        // Si la informacion no esta disponible desde la app
        if (grade == NOT_AVAILABLE) {
            textViewGrade.setText("La calificación de este cuestionario no se encuentra disponible desde la app movil.");

            // Si la info solo esta disponible desde la app
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

    private void goToInfoGradeActivity() {
        Intent intent = new Intent(this, InfoGradeActivity.class);
        intent.putExtra("idCuestionario", idCuestionario);
        intent.putExtra("idAlumno", idAlumno);
        startActivity(intent);
    }

    private void enforceBar() {
        // Forzar y cargar iconos
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Calificación");
    }

    private void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("GradeActivity", "Intent was null");
        else {
            Log.d("GradeActivity", "Intent OK");
            idCuestionario = bundle.getInt("idCuestionario");
            idAlumno = bundle.getString("idAlumno");
            cuestionario = bundle.getString("nombre");
        }
    }

    public void getInfo() {
        grade = getGrade();
        feedBack = getInfoFeedBack();
    }

    private Double getGrade() {
        Double retorno = null;
        idAlumno += ":" + new StudentActivity().getUser().getId();
        UserGradeRequest userGradeRequest = new UserGradeRequest(APIRest.getApi(), idCuestionario, idAlumno);
        try {
            retorno = userGradeRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return retorno;
    }

    private FeedBack getInfoFeedBack() {
        FeedBack retorno = null;
        UserInfoGradeRequest userInfoGradeRequest = new UserInfoGradeRequest(APIRest.getApi(), idCuestionario, idAlumno);
        try {
            retorno = userInfoGradeRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public class UserGradeRequest extends AsyncTask<Void, Void, Double> {
        private Retrofit retrofit;
        private int idCuestionario;
        private String idAlumno;

        public UserGradeRequest(Retrofit retrofit, int idCuestionario, String idAlumno) {
            this.retrofit = retrofit;
            this.idCuestionario = idCuestionario;
            this.idAlumno = idAlumno;
        }

        @Override
        protected Double doInBackground(Void... params) {

            RestService service = retrofit.create(RestService.class);
            Call<APIResponse> call = service.getGrade(idAlumno, idCuestionario);

            double grade = 0;
            try {
                APIResponse apiResponse = call.execute().body();
                grade = Double.parseDouble(apiResponse.getMensaje());
            } catch (IOException e) {
                e.printStackTrace();
            }


            return grade;
        }
    }

    public class UserInfoGradeRequest extends AsyncTask<Void, Void, FeedBack> {

        private Retrofit retrofit;
        private int idCuestionario;
        private String idAlumno;

        public UserInfoGradeRequest(Retrofit retrofit, int idCuestionario, String idAlumno) {
            this.retrofit = retrofit;
            this.idCuestionario = idCuestionario;
            this.idAlumno = idAlumno;
        }

        @Override
        protected FeedBack doInBackground(Void... params) {
            FeedBack feedBack = null;

            RestService service = retrofit.create(RestService.class);
            Call<RespuestaApiFeedback> call = service.getFeedBack(idCuestionario, idAlumno);

            try {
                RespuestaApiFeedback respuestaApiFeedback = call.execute().body();
                feedBack = respuestaApiFeedback.getMensaje();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return feedBack;
        }
    }
}
