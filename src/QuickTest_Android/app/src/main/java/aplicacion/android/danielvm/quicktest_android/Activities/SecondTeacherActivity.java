package aplicacion.android.danielvm.quicktest_android.Activities;

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

import aplicacion.android.danielvm.quicktest_android.Adapters.CourseAdapter;
import aplicacion.android.danielvm.quicktest_android.Adapters.QuestionaryAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Cuestionario;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.R;

public class SecondTeacherActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

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
    private List<Cuestionario> cuestionarios;
    private static final int SWITCH_TO_LIST_VIEW = 0;
    private static final int SWITCH_TO_GRID_VIEW = 1;
    private int idCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_teacher);

        // Recuperamos le identificador del curso seleccionado
        getDataBundle();

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
        switch (item.getItemId()){
            case R.id.itemListView:
                switchView(this.SWITCH_TO_LIST_VIEW);
                return true;
            case R.id.itemGridView:
                switchView(this.SWITCH_TO_GRID_VIEW);
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
}
