package aplicacion.android.danielvm.quicktest_android.Activities;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIRest;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Adapters.TestAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.APIResponse;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.WildCard;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Mensaje;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Pregunta;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Respuesta;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Result;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.TestRequest;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Test;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Utils.RespuestaApi;
import aplicacion.android.danielvm.quicktest_android.Utils.RespuestaApiComodin;
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
    public static String ID_ALUMNO;
    public static String NOMBRE_ALU;
    public static String APE_ALU;

    public static List<WildCard> greenWildCard;
    public static HashMap<Integer, HashSet<Integer>> amberWildCard;
    private HashMap<Integer, String> wildCardType = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        setIdCuestionarioAndKey();
        tests = getContentTest();

        // Inicializamos el estado de los comodines
        initWildCardType();
        // Comprobamos que tenga comodin
        hasWildCard();

        // Instanciamos los elementos de la UI
        button = (Button) findViewById(R.id.btnSendTest);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewTest);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TestAdapter(this.tests, R.layout.recycler_view_item_test, new TestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Test test, int position) {
                test.setComodin("bg-success");
                // Almacenamos la informacion de en que pregunta se ha hecho uso de comodin
                wildCardType.put(position, "bg-success");
                mAdapter.notifyItemChanged(position);
            }
        }, new TestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Test test, int position) {
                test.setComodin("bg-warning");
                wildCardType.put(position, "bg-warning");
                mAdapter.notifyItemChanged(position);
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtenemoslas la informacion del cuestionario resuelto
                HashMap<Integer, Result> results = new TestAdapter(tests, R.layout.recycler_view_item_test, null, null).postTest;
                List<Result> respuestas = new ArrayList<>();

                Iterator<Map.Entry<Integer, Result>> iter = results.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<Integer, Result> item = iter.next();
                    Integer key = item.getKey();
                    Result value = item.getValue();

                    if (wildCardType.containsKey(key)) {
                        String tipo = wildCardType.get(key);
                        value.setTipoComUsado(tipo);
                    }
                    respuestas.add(value);

                }


                ID_ALUMNO = respuestas.get(0).getIdAlumno();
                TestRequest testRequest = new TestRequest(ID_CUESTIONARIO, ID_ALUMNO, NOMBRE_ALU, APE_ALU, respuestas);

                Retrofit retrofit = APIRest.getApi();
                RestService service = retrofit.create(RestService.class);

                Call<APIResponse> call = service.sendTest(testRequest);

                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        int statusCode = response.code();

                        APIResponse apiResponse = response.body();
                        double grade = Double.parseDouble(apiResponse.getMensaje());
                        goToMainActivity(grade);

                        Log.d("TestActivity", "onResponse: " + statusCode);
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Log.d("TestActivity", "onFailure: " + t.getMessage());
                    }
                });

            }
        });
    }

    private void initWildCardType() {
        for (int i = 0; i < tests.size(); i++) {
            wildCardType.put(i, "");
        }
    }

    private void goToMainActivity(double grade) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void setIdCuestionarioAndKey() {

        // Recogemos el nombre introducido en la anterior actividad
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("setIdCuestionarioAndKey", "Intent was null");
        else {
            Log.d("setIdCuestionarioAndKey", "Intent OK");
            ID_CUESTIONARIO = bundle.getInt("idCuestionario");
            CLAVE = bundle.getString("clave");
            NOMBRE_ALU = bundle.getString("nombreAlu");
            APE_ALU = bundle.getString("apeAlu");

        }

    }

    private ArrayList<Test> getContentTest() {
        ArrayList<Test> resultado = new ArrayList<>();
        ContentTestRequest contentTestRequest = new ContentTestRequest(APIRest.getApi(), ID_CUESTIONARIO);
        try {
            List<Mensaje> mensajes = contentTestRequest.execute().get();
            for (Mensaje mensaje : mensajes) {
                Pregunta pregunta = mensaje.getPregunta();
                List<Respuesta> respuestas = mensaje.getRespuestas();
                resultado.add(new Test(pregunta.getTitulo(), respuestas, pregunta.getIdPregunta()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    private void hasWildCard() {
        greenWildCard = getGreenWildCardRequest();
        amberWildCard = getAmberWildCardRequest();
    }

    private ArrayList<WildCard> getGreenWildCardRequest() {
        ArrayList<WildCard> greenWildCard = new ArrayList<>();
        GreenWildCardRequest greenWildCardRequest = new GreenWildCardRequest(APIRest.getApi(), ID_CUESTIONARIO);
        try {
            greenWildCard = (ArrayList<WildCard>) greenWildCardRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return greenWildCard;
    }

    private HashMap<Integer, HashSet<Integer>> getAmberWildCardRequest() {
        ArrayList<WildCard> amberWildCard = new ArrayList<>();
        AmberWildCardRequest amberWildCardRequest = new AmberWildCardRequest(APIRest.getApi(), ID_CUESTIONARIO);

        try {
            amberWildCard = (ArrayList<WildCard>) amberWildCardRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        HashMap<Integer, HashSet<Integer>> retorno = formatAmberWildCard(amberWildCard);

        return retorno;
    }


    private HashMap<Integer, HashSet<Integer>> formatAmberWildCard(ArrayList<WildCard> amberWildCard) {
        HashMap<Integer, HashSet<Integer>> aux = new HashMap<>();
        for (WildCard wildCard : amberWildCard) {
            // Si esta vacio
            if(!aux.containsKey(wildCard.getPregunta_idPregunta())){
                HashSet<Integer> lista = new HashSet<>();
                lista.add(wildCard.getIdRespuesta());
                aux.put(wildCard.getPregunta_idPregunta(), lista);
            }else{
                HashSet<Integer> lista = aux.get(wildCard.getPregunta_idPregunta());
                lista.add(wildCard.getIdRespuesta());
                aux.put(wildCard.getPregunta_idPregunta(), lista);
            }
        }
        return aux;

    }

    public class ContentTestRequest extends AsyncTask<Void, Void, List<Mensaje>> {

        private int idCuestionario;
        private Retrofit retrofit;

        public ContentTestRequest(Retrofit retrofit, int idCuestionario) {
            this.retrofit = retrofit;
            this.idCuestionario = idCuestionario;
        }

        @Override
        protected List<Mensaje> doInBackground(Void... params) {
            RestService service = retrofit.create(RestService.class);
            Call<RespuestaApi> call = service.getTest(String.valueOf(idCuestionario));

            List<Mensaje> messages = null;
            try {
                messages = call.execute().body().getMensaje();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return messages;
        }
    }

    public class GreenWildCardRequest extends AsyncTask<Void, Void, List<WildCard>> {

        private Retrofit retrofit;
        private int idCuestionario;

        public GreenWildCardRequest(Retrofit retrofit, int idCuestionario) {
            this.retrofit = retrofit;
            this.idCuestionario = idCuestionario;
        }

        @Override
        protected List<WildCard> doInBackground(Void... params) {
            List<WildCard> retorno = null;

            RestService service = retrofit.create(RestService.class);
            Call<RespuestaApiComodin> call = service.getGreenWildCard(idCuestionario);

            try {
                RespuestaApiComodin respuestaApiComodin = call.execute().body();
                retorno = respuestaApiComodin.getMensaje();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return retorno;
        }
    }

    public class AmberWildCardRequest extends AsyncTask<Void, Void, List<WildCard>> {

        private Retrofit retrofit;
        private int idCuestionario;

        public AmberWildCardRequest(Retrofit retrofit, int idCuestionario) {
            this.retrofit = retrofit;
            this.idCuestionario = idCuestionario;
        }

        @Override
        protected List<WildCard> doInBackground(Void... params) {

            List<WildCard> retorno = null;

            RestService service = retrofit.create(RestService.class);
            Call<RespuestaApiComodin> call = service.getAmberWildCard(idCuestionario);

            try {
                RespuestaApiComodin respuestaApiComodin = call.execute().body();
                retorno = respuestaApiComodin.getMensaje();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return retorno;
        }
    }
}
