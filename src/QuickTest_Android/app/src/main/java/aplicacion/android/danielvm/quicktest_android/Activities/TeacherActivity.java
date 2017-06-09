package aplicacion.android.danielvm.quicktest_android.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import aplicacion.android.danielvm.quicktest_android.R;

public class TeacherActivity extends AppCompatActivity {

    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        //enforceBar();

    }

    private void enforceBar() {
        myToolbar = (Toolbar) findViewById(R.id.toolbarActivityGrade);
        setSupportActionBar(myToolbar);
        String title = new SecondActivity().user.getFirstname();
        getSupportActionBar().setTitle(title);
    }
}
