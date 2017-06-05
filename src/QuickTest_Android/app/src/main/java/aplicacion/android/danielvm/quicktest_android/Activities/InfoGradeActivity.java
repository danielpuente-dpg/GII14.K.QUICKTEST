package aplicacion.android.danielvm.quicktest_android.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import aplicacion.android.danielvm.quicktest_android.R;

public class InfoGradeActivity extends AppCompatActivity {

    private static double GRADE;
    private static String ID_CUESTIONARIO;
    private static String ID_ALUMNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_grade);

        // Obtenemos la info
        getDataBundle();

        // Obtenemos la calificacion de ese cuestionario



    }

    private void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("InfoGradeActivity", "Intent was null");
        else {
            Log.d("InfoGradeActivity", "Intent OK");
            ID_CUESTIONARIO = bundle.getString("idCuestionario");
            ID_ALUMNO = bundle.getString("idAlumno");
        }
    }


}
