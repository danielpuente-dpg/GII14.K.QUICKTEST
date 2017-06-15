package aplicacion.android.danielvm.quicktestandroid.activities;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aplicacion.android.danielvm.quicktestandroid.api.APIMoodle;
import aplicacion.android.danielvm.quicktestandroid.activities.student.StudentActivity;
import aplicacion.android.danielvm.quicktestandroid.activities.teacher.TeacherActivity;
import aplicacion.android.danielvm.quicktestandroid.adapters.CourseAdapter;
import aplicacion.android.danielvm.quicktestandroid.models.moodle.Course;
import aplicacion.android.danielvm.quicktestandroid.models.moodle.Role;
import aplicacion.android.danielvm.quicktestandroid.R;

/**
 * Clase CourseActivity encarga de mostrar los cursos para ese usuario.
 * @author Daniel Puente Gabarri.
 */
public class CourseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "CourseActivity";
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
    private MainActivity activity = new MainActivity();


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

    /**
     * Metodo encargado de forzar la carga del action bar.
     */
    private void enforceIconBar() {
        getSupportActionBar().setIcon(R.mipmap.ic_action_bar_student);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cursos");
    }


    /**
     * Metodo encargado de obtener los cursos en los que se encuentra matriculado
     * el usuario,
     * @return List<Course>, retorno.
     */
    private List<Course> loadCourses() {
        List<Course> retorno = new ArrayList<>();
        HashMap<Integer, Course> coursesById = activity.getQuestionnariesById();
        HashMap<Integer, Role> coursesByRol = activity.getCoursesByRol();

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


    /**
     * Metodo encargado de direccionar al siguiente activity en funcion del rol del usuario
     * en dicho curso.
     * @param parent, parent.
     * @param view, view.
     * @param position, position.
     * @param id, id.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "Curso: " + this.courses.get(position));
        goToMainOrSecondTeacherActivity(position);
    }

    /**
     * Metodo encargado de la logica de onItemClick en funcion del rol del usuario.
     * @param position, position.
     */
    private void goToMainOrSecondTeacherActivity(int position) {
        ROLE_OF_USER = getUserRolInCourse(position);
        if (ROLE_OF_USER.equals(APIMoodle.IS_EDIT_TEACHER) || ROLE_OF_USER.equals(APIMoodle.IS_TEACHER)) {
            goToTeacherActivity(position);
        } else if (ROLE_OF_USER.equals(APIMoodle.IS_STUDENT)) {
            goToStudentActivity(position);
        } else {
            Log.d(TAG, "Rol: " + this.courses.get(position).getRol() + " no contemplado");
        }
    }

    /**
     * Metodo encargado de proporcionar el rol del usuario en funcion del curso seleccionado.
     * @param position, position.
     * @return Strinf, rol.
     */
    private String getUserRolInCourse(int position) {
        return this.courses.get(position).getRol();
    }

    /**
     * Metodo encargado de direccionar al activity del alumno.
     * @param position, position.
     */
    private void goToStudentActivity(int position) {
        Intent intent = new Intent(this, StudentActivity.class);
        intent.putExtra("idCourse", this.courses.get(position).getId());
        startActivity(intent);
    }

    /**
     * Metodo encargado de direccionar al activity del profesor.
     * @param position, position.
     */
    private void goToTeacherActivity(int position) {
        Intent intent = new Intent(this, TeacherActivity.class);
        intent.putExtra("idCourse", this.courses.get(position).getId());
        startActivity(intent);
    }

    /**
     * Metodo encargado de inflar la vista del action bar.
     * @param menu, menu.
     * @return boolean, true.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_course, menu);

        // Referencias de los menus
        this.itemListView = menu.findItem(R.id.itemListView);
        this.itemGridView = menu.findItem(R.id.itemGridView);

        return true;
    }

    /**
     * Metodo encargado de la logica a seguir en funcion del boton seleccionado del action bar.
     * @param item, item.
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
}

