package aplicacion.android.danielvm.quicktest_android.Activities.Teacher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import aplicacion.android.danielvm.quicktest_android.Activities.MainActivity;
import aplicacion.android.danielvm.quicktest_android.Adapters.Teacher.QuestionnaireAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Questionnaire;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Utils.Util;

/**
 * Clase TeacherActivity encargada de mostrar los cuestionarios de QuickTest
 * pertenecientes a ese profesor en dicho curso.
 * @author Daniel Puente Gabarri.
 */
public class TeacherActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // Shared Preferences
    private SharedPreferences prefs;

    // Elementos de la UI
    private ListView listView;
    private GridView gridView;

    // Elementos que forman el MENU de la UI
    private MenuItem itemListView;
    private MenuItem itemGridView;

    // Adaptador
    private QuestionnaireAdapter listViewAdapter;
    private QuestionnaireAdapter gridViewAdapter;

    // Atributos
    private static List<Questionnaire> questionnaries;
    private static final int SWITCH_TO_LIST_VIEW = 0;
    private static final int SWITCH_TO_GRID_VIEW = 1;
    private static int idCourse;
    private static String tokenWebService;
    private MainActivity activity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_teacher);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        // Recuperamos le identificador del curso seleccionado
        getDataBundle();


        MainActivity activity = new MainActivity();
        tokenWebService = activity.getToken();

        // Forzamos la carga del icono de la aplicacion
        enforceIconBar();

        // Añadimos los cursos de eso profesor
        this.questionnaries = loadCuestionarios();

        // Instanciamos los elementos de la UI
        this.listView = (ListView) findViewById(R.id.listViewQuestionary);
        this.gridView = (GridView) findViewById(R.id.gridViewQuestionary);

        // Evento que controla la accion a realizar al clicked sobre un Item
        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        // Enlazamos con el adaptador de Cursos
        this.listViewAdapter = new QuestionnaireAdapter(this, R.layout.list_view_questionary, questionnaries);
        this.gridViewAdapter = new QuestionnaireAdapter(this, R.layout.grid_view_questionary, questionnaries);

        this.listView.setAdapter(listViewAdapter);
        this.gridView.setAdapter(gridViewAdapter);

        // Registre los cambios sobre ambas vistas
        registerForContextMenu(this.listView);
        registerForContextMenu(this.gridView);
    }

    /**
     * Metodo encargado de obtener los cuestionarios.
     * @return List<Questionnaire>, questionnaires.
     */
    private List<Questionnaire> loadCuestionarios() {
        List<Questionnaire> questionnaires = activity.getQuestionnairesInACourse().get(idCourse);
        return questionnaires;
    }

    /**
     * Metodo encarcado de proporcionar un cuestionario en funcion de la posicion dada.
     * @param position, position
     * @return Questionnaire, questionnaire.
     */
    public Questionnaire getQuestionaryByPosition(int position) {
        return this.questionnaries.get(position);
    }

    /**
     * Metodo encargado de dar el id del curso.
     * @return int, idCourse.
     */
    public int getIdCourse() {
        return idCourse;
    }

    /**
     * Metodo encargado de proporcionar el token del web service.
     * @return String, tokenWebService.
     */
    public String getTokenWebService(){
        return tokenWebService;
    }

    /**
     * Metodo encargado de recuperar informacion proporciona por el anterior activity.
     */
    private void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("TeacherActivity", "Intent was null");
        else {
            Log.d("TeacherActivity", "Intent OK");
            idCourse = bundle.getInt("idCourse");
        }
    }

    /**
     * Metodo encargado de forzar la carga del action bar.
     */
    private void enforceIconBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cuestionarios");
    }

    /**
     * Metodo encargado de direccionar al siguiente activity en funcion del cuestionario dado.
     * @param parent, parent.
     * @param view, parent.
     * @param position, parent.
     * @param id, parent.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("TeacherActivity", "Questionnaire: id: " + this.questionnaries.get(position).getIdCuestionario());
        goToStudentsGradesActivity(position);
    }

    /**
     * Metodo encargado de direccionar al siguiente activity en funcion del cuestionario dado.
     * @param position, position.
     */
    private void goToStudentsGradesActivity(int position) {
        Intent intent = new Intent(TeacherActivity.this, StudentsGradesActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    /**
     * Metodo encargado de inflar el action bar del activity.
     * @param menu, menu.
     * @return boolean, true.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_course_teacher, menu);

        // Referencias de los menus
        this.itemListView = menu.findItem(R.id.itemListView);
        this.itemGridView = menu.findItem(R.id.itemGridView);

        return true;
    }

    /**
     * Metodo encargado de activar o desactivar las propiedas del ListView o GridView,
     * en funcion de la opcion selecconada.
     * @param item, item
     * @return boolean, true.
     */
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

    /**
     * Metodo encargado de activar o desactivar las propiedas del ListView o GridView,
     * en funcion de la opcion selecconada.
     * @param option, option.
     */
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

    /**
     * Metodo encargado de cerrar sesion.
     */
    private void logOut() {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        // Al hacer clicked Atras, cerramos la App
        intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentLogin);
    }
}
