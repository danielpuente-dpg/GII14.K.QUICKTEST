package aplicacion.android.danielvm.quicktest_android.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIRest;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.APIResponse;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.GradeRequest;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Token;
import aplicacion.android.danielvm.quicktest_android.R;
import retrofit2.Call;
import retrofit2.Retrofit;

public class GradeActivity extends AppCompatActivity {

    private TextView textViewGrade;
    private ImageView imageView;

    private Toolbar myToolbar;

    private double grade;
    private int state;
    private String idAlumno;
    private int idCuestionario;
    private String cuestionario;

    private static final int FAST = 0;
    private static final int NORMAL = 1;
    private static final int SLOW = 2;
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
        imageView = (ImageView) findViewById(R.id.imageViewStatusGradeActivity);

        myToolbar.setNavigationIcon(R.drawable.ic_flecha_white);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
                finish();
            }
        });
        if(grade != NOT_AVAILABLE){
            // Cambiamos la info
            grade *= 10;
            textViewGrade.setText("Su calificación final en este cuestionario es " + grade + "/10");

        /*if (state == FAST) {
            imageView.setImageResource(R.drawable.status_slow);
        } else if (state == SLOW) {
            imageView.setImageResource(R.drawable.status_slow);
        } else {
            imageView.setImageResource(R.drawable.status_slow);
        }*/

            imageView.setImageResource(R.drawable.status_slow);
        }else{
            textViewGrade.setText("La calificación de este cuestionario no se encuentra disponible desde la app movil.");
        }



    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void enforceBar() {
        // Forzar y cargar iconos
        myToolbar = (Toolbar) findViewById(R.id.toolbarActivityGrade);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(cuestionario);
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
    }

    private Double getGrade() {
        Double retorno = null;
        idAlumno += ":" + new MainActivity().getUser().getId();
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
}
