package aplicacion.android.danielvm.quicktest_android.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import aplicacion.android.danielvm.quicktest_android.R;

public class InfoGradeActivity extends AppCompatActivity {

    private static double GRADE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        // Obtenemos la nota
        getGrade();

        Toast.makeText(this, "Resultado: " + GRADE, Toast.LENGTH_SHORT).show();


    }

    private void getGrade() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("InfoGradeActivity", "Intent was null");
        else {
            Log.d("InfoGradeActivity", "Intent OK");
            GRADE = bundle.getDouble("grade");

        }
    }
}
