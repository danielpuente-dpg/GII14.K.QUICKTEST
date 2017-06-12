package aplicacion.android.danielvm.quicktest_android.Activities;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Activities.Student.MainActivity;
import aplicacion.android.danielvm.quicktest_android.Activities.Teacher.SecondTeacherActivity;
import aplicacion.android.danielvm.quicktest_android.Adapters.CourseAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Role;
import aplicacion.android.danielvm.quicktest_android.R;

public class CourseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

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
    private String ROLE_OF_USER;
    private static final String IS_STUDENT = "student";
    private static final String IS_EDIT_TEACHER = "editingteacher";
    private static final String IS_TEACHER = "teacher";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Forzamos la carga del icono de la aplicacion
        enforceIconBar();

        // Añadimos los cursos de eso profesor
        this.courses = loadCourses();

        // Instanciamos los elementos de la UI
        this.listView = (ListView) findViewById(R.id.listViewCourse);
        this.gridView = (GridView) findViewById(R.id.gridViewCourse);

        // Evento que controla la accion a realizar al clicked sobre un Item
        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        // Enlazamos con el adaptador de Cursos
        this.listViewAdapter = new CourseAdapter(this, R.layout.list_view_course, courses);
        this.gridViewAdapter = new CourseAdapter(this, R.layout.grid_view_course, courses);

        this.listView.setAdapter(listViewAdapter);
        this.gridView.setAdapter(gridViewAdapter);

        // Registre los cambios sobre ambas vistas
        registerForContextMenu(this.listView);
        registerForContextMenu(this.gridView);
    }

    private void enforceIconBar() {
        getSupportActionBar().setIcon(R.mipmap.ic_action_bar_student);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cursos");
    }

    private List<Course> loadCourses() {
        List<Course> retorno = new ArrayList<>();
        HashMap<Integer, Course> coursesById = new SecondActivity().coursesById;
        HashMap<Integer, Role> coursesByRol = new SecondActivity().coursesByRol;

        for(Integer idCourse : coursesById.keySet()){
            Course course = coursesById.get(idCourse);
            if(coursesByRol.containsKey(idCourse)){
                String rolType = coursesByRol.get(idCourse).getShortname();
                course.setRol(rolType);
                retorno.add(course);
            }
        }
        return retorno;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("CourseActivity", "Curso: " + this.courses.get(position));
        goToMainOrSecondTeacherActivity(position);
    }

    private void goToMainOrSecondTeacherActivity(int position) {
        ROLE_OF_USER = getUserRolInCourse(position);
        if (ROLE_OF_USER.equals(IS_EDIT_TEACHER) || ROLE_OF_USER.equals(IS_TEACHER)) {
            goToSecondTeacherActivity(position);
        } else if (ROLE_OF_USER.equals(IS_STUDENT)) {
            goToMainActivity(position);
        } else {
            Log.d("CourseActivity", "Rol: " + this.courses.get(position).getRol() + " no contemplado");
        }
    }

    private String getUserRolInCourse(int position) {
        return this.courses.get(position).getRol();
    }

    private void goToMainActivity(int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("idCourse", this.courses.get(position).getId());
        startActivity(intent);
    }

    private void goToSecondTeacherActivity(int position) {
        Intent intent = new Intent(this, SecondTeacherActivity.class);
        intent.putExtra("idCourse", this.courses.get(position).getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_course, menu);

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

