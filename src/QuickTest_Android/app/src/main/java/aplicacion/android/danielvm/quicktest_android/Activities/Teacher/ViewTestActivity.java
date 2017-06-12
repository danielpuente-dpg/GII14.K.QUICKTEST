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
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Mensaje;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Pregunta;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Respuesta;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Cuestionario;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Test;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Requests.ContentTestRequest;

public class ViewTestActivity extends AppCompatActivity {

    // Atributos para el Adapter
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private List<Test> tests;
    private Cuestionario cuestionario;
    private int idCuestionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);

        // Recuperamos la información
        getDataBundle();

        ThirdTeacherActivity activity =  new ThirdTeacherActivity();
        this.cuestionario = activity.getCuestionario();

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

    private void enforceIconBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String title = cuestionario.getDescripcion();
        getSupportActionBar().setTitle(title);
    }

    private ArrayList loadTest() {
        return getTest();
    }

    private ArrayList<Test> getTest(){
        ArrayList<Test> retorno = new ArrayList<>();
        ContentTestRequest contentTestRequest = new ContentTestRequest(APIRest.getApi(), idCuestionario);
        try {
            List<Mensaje> mensajes = contentTestRequest.execute().get();
            for (Mensaje mensaje : mensajes) {
                Pregunta pregunta = mensaje.getPregunta();
                List<Respuesta> respuestas = mensaje.getRespuestas();
                retorno.add(new Test(pregunta.getTitulo(), respuestas, pregunta.getIdPregunta()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("ViewTestActivity", "Intent was null");
        else {
            Log.d("ViewTestActivity", "Intent OK");
            idCuestionario = bundle.getInt("idCuestionario");
        }
    }
}
