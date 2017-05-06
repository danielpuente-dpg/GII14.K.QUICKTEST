package aplicacion.android.danielvm.quicktest_android.Activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.API.APIRest;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Adapters.TestAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Mensaje;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Pregunta;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Respuesta;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.TestRequest;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Test;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Utils.RespuestaApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TestActivity extends AppCompatActivity {

    // Atributos para el Adapter
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button button;

    // Atributos
    private List<Test> tests;
    private int ID_CUESTIONARIO;
    public static String CLAVE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tests = new ArrayList<>();
        setIdCuestionarioAndKey();
        getTest(ID_CUESTIONARIO);

        // Instanciamos los elementos de la UI

        button = (Button) findViewById(R.id.btnSendTest);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewTest);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TestAdapter(this.tests, R.layout.recycler_view_item_test);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtenemoslas la informacion del cuestionario resuelto
                HashMap<Integer, TestRequest> postTest = new TestAdapter(tests, R.layout.recycler_view_item_test).postTest;




                //Toast.makeText(TestActivity.this, postTest.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getTest(int id) {
        Retrofit retrofit = APIRest.getApi();
        RestService service = retrofit.create(RestService.class);

        Call<RespuestaApi> call = service.getTest(String.valueOf(id));

        call.enqueue(new Callback<RespuestaApi>() {
            @Override
            public void onResponse(Call<RespuestaApi> call, Response<RespuestaApi> response) {

                if (response.isSuccessful()) {

                    Log.d("Tests", "OnResponse: " + response.code());

                    List<Mensaje> messages = response.body().getMensaje();
                    addTests(messages);
                } else {
                    Toast.makeText(TestActivity.this, "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaApi> call, Throwable t) {

            }
        });
    }

    private void addTests(List<Mensaje> messages) {
        for (Mensaje m : messages) {
            Pregunta pregunta = m.getPregunta();
            List<Respuesta> respuestas = m.getRespuestas();
            tests.add(new Test(pregunta.getTitulo(), respuestas, pregunta.getIdPregunta()));
        }
    }

    public void setIdCuestionarioAndKey() {

        // Recogemos el nombre introducido en la anterior actividad
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("***DEBUG****", "Intent was null");
        else {
            Log.d("**** DEBUG ***", "Intent OK");
            ID_CUESTIONARIO = bundle.getInt("idCuestionario");
            CLAVE = bundle.getString("clave");
        }

    }
}
