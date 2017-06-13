package aplicacion.android.danielvm.quicktest_android.Activities.Student;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIRest;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Adapters.Student.TestAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.APIResponse;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Mensaje;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Pregunta;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Respuesta;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Result;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.TestRequest;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.WildCard;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Questionnaire;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Test;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Requests.APIRest.AmberWildCardRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIRest.ContentTestRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIRest.GreenWildCardRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Clase TestActivity encargada de mostrar al alumno el nameQuestionnaire a resolver.
 *
 * @author Daniel Puente Gabarri.
 */
public class TestActivity extends AppCompatActivity {

    // Atributos para el Adapter
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button button;

    // Atributos
    private List<Test> tests;
    private int idQuestionnaire;
    public static String key;
    public static String idStudent;
    public static String nameStudent;
    public static String surname;
    private Questionnaire questionnaire;
    private int position;
    public static List<WildCard> greenWildCard;
    public static HashMap<Integer, HashSet<Integer>> amberWildCard;
    private HashMap<Integer, String> wildCardType = new HashMap<>();
    private int idCourse;
    private StudentActivity activity = new StudentActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Recuperamos informacion
        getDataBundle();
        questionnaire = activity.getDataExternalTools().get(position);

        // Forzamos la carga del icono de la aplicacion
        enforceIconBar();

        // Obtenemos el nameQuestionnaire
        tests = getContentTest();

        // Obtenemos para ese nameQuestionnaire sus comodines
        getWildCard();

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
                // Obtenemoslas la informacion del questionnaire resuelto y lo enviamos
                sendResolvedQuestionnaire();
            }
        });
    }

    /**
     * Metodo encargado de obtener lainformacion del nameQuestionnaire y enviar las answers.
     */
    private void sendResolvedQuestionnaire() {
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
        idCourse = activity.getIdCourse();
        idStudent = respuestas.get(0).getIdAlumno();

        TestRequest testRequest = new TestRequest(idQuestionnaire, idStudent, nameStudent, surname, respuestas);

        Retrofit retrofit = APIRest.getApi();
        RestService service = retrofit.create(RestService.class);

        Call<APIResponse> call = service.sendTest(testRequest);

        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                int statusCode = response.code();

                APIResponse apiResponse = response.body();
                double grade = Double.parseDouble(apiResponse.getMensaje());
                if (grade > 0) {
                    Log.d("TestActivity", "onResponse: " + statusCode);
                    goToStudentActivity(idCourse);
                } else {
                    Log.d("TestActivity", "onResponse: " + statusCode + ", grade is < 0");
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Log.d("TestActivity", "onFailure: " + t.getMessage());
            }
        });
    }

    /**
     * Metodo encargado de forzar la carga del action bar.
     */
    private void enforceIconBar() {
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(questionnaire.getDescripcion());
    }

    /**
     * Metodo encargado de inicializar el estado de los comodines.
     */
    private void initWildCardType() {
        for (int i = 0; i < tests.size(); i++) {
            wildCardType.put(i, "");
        }
    }

    /**
     * Metodo encargado de direccionar al StudentActivity al finalizar el nameQuestionnaire.
     *
     * @param idCourse, idCourse.
     */
    private void goToStudentActivity(int idCourse) {
        Intent intent = new Intent(this, StudentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("idCourse", idCourse);
        startActivity(intent);
    }

    /**
     * Metodo encargado de recuperar la informacion proporcionada por el activity de origen.
     */
    public void getDataBundle() {
        // Recogemos el nombre introducido en la anterior actividad
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("TestActivity", "Intent was null");
        else {
            Log.d("TestActivity", "Intent OK");
            idQuestionnaire = bundle.getInt("idQuestionnaire");
            key = bundle.getString("key");
            nameStudent = bundle.getString("nameStudent");
            surname = bundle.getString("surname");
            position = bundle.getInt("position");
        }

    }

    /**
     * Metodo encargado de obtener el nameQuestionnaire.
     *
     * @return ArrayList<Test>, tests.
     */
    private ArrayList<Test> getContentTest() {
        ArrayList<Test> resultado = new ArrayList<>();
        ContentTestRequest contentTestRequest = new ContentTestRequest(APIRest.getApi(), idQuestionnaire);
        try {
            List<Mensaje> messages = contentTestRequest.execute().get();
            for (Mensaje mensaje : messages) {
                Pregunta question = mensaje.getPregunta();
                List<Respuesta> answers = mensaje.getRespuestas();
                resultado.add(new Test(question.getTitulo(), answers, question.getIdPregunta()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("TestActivity", "getContentTest:" + resultado.size());
        return resultado;
    }

    /**
     * Metodo encargado de obtener los comodines para el nameQuestionnaire
     */
    private void getWildCard() {
        // Inicializamos el estado de los comodines
        initWildCardType();
        greenWildCard = getGreenWildCardRequest();
        Log.d("TestActivity", "greenWildCard:" + greenWildCard.size());
        amberWildCard = getAmberWildCardRequest();
        Log.d("TestActivity", "amberWildCard:" + amberWildCard.size());
    }

    /**
     * Metodo encargado de obtener los comodines verdes del nameQuestionnaire.
     *
     * @return ArrayList<WildCard>, greenWildCard.
     */
    private ArrayList<WildCard> getGreenWildCardRequest() {
        ArrayList<WildCard> greenWildCard = new ArrayList<>();
        GreenWildCardRequest greenWildCardRequest = new GreenWildCardRequest(APIRest.getApi(), idQuestionnaire);
        try {
            greenWildCard = (ArrayList<WildCard>) greenWildCardRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return greenWildCard;
    }

    /**
     * Metodo encargado de obtener los comodines ambar del nameQuestionnaire.
     *
     * @return ArrayList<WildCard>, amberWildCard.
     */
    private HashMap<Integer, HashSet<Integer>> getAmberWildCardRequest() {
        ArrayList<WildCard> amberWildCard = new ArrayList<>();
        AmberWildCardRequest amberWildCardRequest = new AmberWildCardRequest(APIRest.getApi(), idQuestionnaire);

        try {
            amberWildCard = (ArrayList<WildCard>) amberWildCardRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        HashMap<Integer, HashSet<Integer>> retorno = new HashMap<>();
        if (amberWildCard.size() > 0) {
            retorno = formatAmberWildCard(amberWildCard);
        }
        return retorno;
    }

    /**
     * Metodo encargado de convertir los comodines ambar para su posterior uso.
     *
     * @param amberWildCard, amberWildCard
     * @return HashMap<Integer, HashSet<Integer>>, aux.
     */
    private HashMap<Integer, HashSet<Integer>> formatAmberWildCard(ArrayList<WildCard> amberWildCard) {
        HashMap<Integer, HashSet<Integer>> aux = new HashMap<>();
        for (WildCard wildCard : amberWildCard) {
            // Si esta vacio
            if (!aux.containsKey(wildCard.getPregunta_idPregunta())) {
                HashSet<Integer> lista = new HashSet<>();
                lista.add(wildCard.getIdRespuesta());
                aux.put(wildCard.getPregunta_idPregunta(), lista);
            } else {
                HashSet<Integer> lista = aux.get(wildCard.getPregunta_idPregunta());
                lista.add(wildCard.getIdRespuesta());
                aux.put(wildCard.getPregunta_idPregunta(), lista);
            }
        }
        return aux;

    }

}
