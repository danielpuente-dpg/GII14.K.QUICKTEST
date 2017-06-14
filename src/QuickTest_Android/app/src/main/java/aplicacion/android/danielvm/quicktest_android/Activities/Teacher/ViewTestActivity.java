package aplicacion.android.danielvm.quicktest_android.Activities.Teacher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIRest;
import aplicacion.android.danielvm.quicktest_android.Adapters.Teacher.TestAdapterViewTeacher;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Message;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Question;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Answer;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Questionnaire;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Test;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Requests.APIRest.ContentTestRequest;

/**
 * Clase ViewTestActivity encargada de mostrar un cuestionario al profesor.
 *
 * @author Daniel Puente Gabarri.
 */
public class ViewTestActivity extends AppCompatActivity {

    // Atributos para el Adapter
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private List<Test> tests;
    private Questionnaire questionnaire;
    private int idQuestionnarie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);

        // Recuperamos la información
        getDataBundle();

        StudentsGradesActivity activity = new StudentsGradesActivity();
        this.questionnaire = activity.getQuestionnaire();

        // Forzamos la carga del icono de la aplicacion
        enforceIconBar();

        this.tests = loadTest();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewTestViewTeacher);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TestAdapterViewTeacher(this.tests, R.layout.recycler_view_item_test_view_teacher);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Metodo encargado de forzar la carga del action bar.
     */
    private void enforceIconBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String title = questionnaire.getDescription();
        getSupportActionBar().setTitle(title);
    }

    /**
     * Metodo devuelve el cuestionario.
     *
     * @return ArrayList<Test>, tests.
     */
    private ArrayList<Test> loadTest() {
        return getTest();
    }

    /**
     * Metodo que obtiene el cuestionario en funcion del identificador dado.
     *
     * @return ArrayList<Test>, tests.
     */
    private ArrayList<Test> getTest() {
        ArrayList<Test> retorno = new ArrayList<>();
        ContentTestRequest contentTestRequest = new ContentTestRequest(APIRest.getApi(), idQuestionnarie);
        try {
            List<Message> messages = contentTestRequest.execute().get();
            for (Message msg : messages) {
                Question question = msg.getQuestion();
                List<Answer> answers = msg.getAnswers();
                retorno.add(new Test(question.getTitulo(), answers, question.getIdPregunta()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    /**
     * Metodo encargado de recuperar informacion del anterior activity.
     */
    public void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("ViewTestActivity", "Intent was null");
        else {
            Log.d("ViewTestActivity", "Intent OK");
            idQuestionnarie = bundle.getInt("idQuestionnarie");
        }
    }
}
