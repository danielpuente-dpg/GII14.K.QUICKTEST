package aplicacion.android.danielvm.quicktest_android.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import aplicacion.android.danielvm.quicktest_android.Adapters.CourseAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Cuestionario;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.R;

public class TeacherActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    // Elementos de la UI
    private ListView listView;
    private GridView gridView;

    // Elementos que forman el MENU de la UI
    private MenuItem itemListView;
    private MenuItem itemGridView;

    // Adaptador
    private CourseAdapter listViewAdapter;
    private CourseAdapter gridViewAdapter;

    // Atributos
    private List<Course> courses;
    private static final int SWITCH_TO_LIST_VIEW = 0;
    private static final int SWITCH_TO_GRID_VIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        // Forzamos la carga del icono de la aplicacion
        enforceIconBar();

        // Añadimos los cursos de eso profesor
        this.courses = loadCourses();

        // Instanciamos los elementos de la UI
        this.listView = (ListView) findViewById(R.id.listViewCourseOfTeacher);
        this.gridView = (GridView) findViewById(R.id.gridViewCourseOfTeacher);

        // Evento que controla la accion a realizar al clicked sobre un Item
        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        // Enlazamos con el adaptador de Cursos
        this.listViewAdapter = new CourseAdapter(this, R.layout.list_view_course_teacher, courses);
        this.gridViewAdapter = new CourseAdapter(this, R.layout.grid_view_course_teacher, courses);

        this.listView.setAdapter(listViewAdapter);
        this.gridView.setAdapter(gridViewAdapter);

        // Registre los cambios sobre ambas vistas
        registerForContextMenu(this.listView);
        registerForContextMenu(this.gridView);

    }

    private List<Course> loadCourses() {
        List<Course> retorno = new ArrayList<>();
        HashMap<Integer, Course> coursesById = new SecondActivity().coursesById;

        for(Integer idCourse : coursesById.keySet()){
            Course course = coursesById.get(idCourse);
            retorno.add(course);
        }
        return retorno;

    }

    private void enforceIconBar() {
        getSupportActionBar().setIcon(R.mipmap.ic_action_bar_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("TeacherActivity", "Curso: " + this.courses.get(position));
        goToSecondTeacherActivity(position);
    }

    private void goToSecondTeacherActivity(int position) {
        Intent intent =  new Intent(TeacherActivity.this, SecondTeacherActivity.class);
        intent.putExtra("idCourse", this.courses.get(position).getId());
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
