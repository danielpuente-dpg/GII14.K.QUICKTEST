package aplicacion.android.danielvm.quicktest_android.Activities.Teacher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Activities.LoginActivity;
import aplicacion.android.danielvm.quicktest_android.Activities.SecondActivity;
import aplicacion.android.danielvm.quicktest_android.Adapters.Teacher.QuestionaryAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Cuestionario;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Utils.Util;

public class SecondTeacherActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // Shared Preferences
    private SharedPreferences prefs;

    // Elementos de la UI
    private ListView listView;
    private GridView gridView;

    // Elementos que forman el MENU de la UI
    private MenuItem itemListView;
    private MenuItem itemGridView;

    // Adaptador
    private QuestionaryAdapter listViewAdapter;
    private QuestionaryAdapter gridViewAdapter;

    // Atributos
    private static List<Cuestionario> cuestionarios;
    private static final int SWITCH_TO_LIST_VIEW = 0;
    private static final int SWITCH_TO_GRID_VIEW = 1;
    private static int idCourse;
    private static String tokenWebService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_teacher);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        // Recuperamos le identificador del curso seleccionado
        getDataBundle();


        SecondActivity activity = new SecondActivity();
        tokenWebService = activity.getToken();

        // Forzamos la carga del icono de la aplicacion
        enforceIconBar();

        // Añadimos los cursos de eso profesor
        this.cuestionarios = loadCuestionarios();

        // Instanciamos los elementos de la UI
        this.listView = (ListView) findViewById(R.id.listViewQuestionary);
        this.gridView = (GridView) findViewById(R.id.gridViewQuestionary);

        // Evento que controla la accion a realizar al clicked sobre un Item
        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        // Enlazamos con el adaptador de Cursos
        this.listViewAdapter = new QuestionaryAdapter(this, R.layout.list_view_questionary, cuestionarios);
        this.gridViewAdapter = new QuestionaryAdapter(this, R.layout.grid_view_questionary, cuestionarios);

        this.listView.setAdapter(listViewAdapter);
        this.gridView.setAdapter(gridViewAdapter);

        // Registre los cambios sobre ambas vistas
        registerForContextMenu(this.listView);
        registerForContextMenu(this.gridView);
    }

    private List<Cuestionario> loadCuestionarios() {
        return new SecondActivity().questionariesInACourse.get(idCourse);
    }

    public Cuestionario getQuestionaryByPosition(int position) {
        return this.cuestionarios.get(position);
    }

    public int getIdCourse() {
        return idCourse;
    }

    public String getTokenWebService(){
        return tokenWebService;
    }

    private void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("SecondTeacherActivity", "Intent was null");
        else {
            Log.d("SecondTeacherActivity", "Intent OK");
            idCourse = bundle.getInt("idCourse");
        }
    }

    private void enforceIconBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cuestionarios");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("SecondTeacherActivity", "Cuestionario: id: " + this.cuestionarios.get(position).getIdCuestionario());
        goToThirdTeacherActivity(position);
    }

    private void goToThirdTeacherActivity(int position) {
        Intent intent = new Intent(SecondTeacherActivity.this, ThirdTeacherActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_course_teacher, menu);

        // Referencias de los menus
        this.itemListView = menu.findItem(R.id.itemListView);
        this.itemGridView = menu.findItem(R.id.itemGridView);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemListView:
                switchView(this.SWITCH_TO_LIST_VIEW);
                return true;
            case R.id.itemGridView:
                switchView(this.SWITCH_TO_GRID_VIEW);
                return true;
            case R.id.log_out_teacher:
                logOut();
                return true;
            case R.id.log_forget_out_teacher:
                Util.removeUserToSharedPreferences(prefs);
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchView(int option) {
        if (option == SWITCH_TO_LIST_VIEW) {
            if (this.listView.getVisibility() == View.INVISIBLE) {

                // Logica para Grid View
                this.gridView.setVisibility(View.INVISIBLE);
                this.itemGridView.setVisible(true);
                // Logica para List View
                this.listView.setVisibility(View.VISIBLE);
                this.itemListView.setVisible(false);

            }
        } else if (option == SWITCH_TO_GRID_VIEW) {
            if (this.gridView.getVisibility() == View.INVISIBLE) {

                this.listView.setVisibility(View.INVISIBLE);
                this.itemListView.setVisible(true);

                this.gridView.setVisibility(View.VISIBLE);
                this.itemGridView.setVisible(false);
            }
        }
    }

    private void logOut() {
        Intent intentLogin = new Intent(this, LoginActivity.class);

        // Al hacer clicked Atras, cerramos la App
        intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intentLogin);
    }
}
