package aplicacion.android.danielvm.quicktest_android.Activities.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIRest;
import aplicacion.android.danielvm.quicktest_android.Adapters.Teacher.StudentAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.EnrolCourse;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Questionnaire;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Student;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.UserEnrol;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Requests.APIRest.StatusQuestionnaireRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIRest.UserGradeRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle.UsersEnrolledInCourse;

/**
 * Clase StudentsGradesActivity encargada de mostrar al profesor las calificaciones obtenidas por sus alumno.
 *
 * @author Daniel Puente Gabarri
 */
public class StudentsGradesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // Elementos de la UI
    private ListView listView;

    // Adaptador
    private StudentAdapter listViewAdapter;

    // Atributos
    private int position;
    private static Questionnaire questionnaire;
    private int idQuestionnarie;
    private int idCourse;
    private String tokenWebService;
    private static final int RESOLVED = 1;
    private static final int UNRESOLVED = 0;
    private static final int NOT_INFO = -1;
    private UserEnrol[] usersEnrol;
    private List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_teacher);

        getDataBundle();
        getOthersData();

        // Forzamos la carga del icono de la aplicacion
        enforceIconBar();

        // Obtenemos los estudiantes dado un curso y questionnaire
        this.students = loadStudents();
        Log.d("StudentsGradesActivity", this.students.size() + "");

        // Instanciamos los elementos de la UI
        this.listView = (ListView) findViewById(R.id.listViewStudentGrade);

        // Evento que controla la accion a realizar al clicked sobre un Item
        this.listView.setOnItemClickListener(this);


        // Enlazamos con el adaptador de Cursos
        this.listViewAdapter = new StudentAdapter(this, R.layout.list_view_student_grade, students);

        this.listView.setAdapter(listViewAdapter);

        // Registre los cambios sobre ambas vistas
        registerForContextMenu(this.listView);

    }

    /**
     * Metodo encargado de forzar la carga del action bar.
     */
    private void enforceIconBar() {
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(questionnaire.getDescripcion());
    }

    /**
     * Metodo encargado de obtener informacion necesaria del TeacherActivity.
     */
    private void getOthersData() {
        TeacherActivity activity = new TeacherActivity();
        questionnaire = activity.getQuestionaryByPosition(position);
        idQuestionnarie = questionnaire.getIdCuestionario();
        idCourse = activity.getIdCourse();
        tokenWebService = activity.getTokenWebService();
        usersEnrol = getUserEnrolsInCourse();
    }

    /**
     * Metodo encargado de obtener los usuarios matriculados en curso.
     *
     * @return UserEnrol[], usersEnrol.
     */
    private UserEnrol[] getUserEnrolsInCourse() {

        UsersEnrolledInCourse usersEnrolledInCourse = new UsersEnrolledInCourse(APIMoodle.getApi(), tokenWebService, idCourse);
        UserEnrol[] usersEnrol = null;
        try {
            usersEnrol = usersEnrolledInCourse.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("StudentsGradesActivity", "usersEnrol: " + usersEnrol.length);
        return usersEnrol;
    }

    /**
     * Metododo encargado de obtener los alumnos.
     * @return ArrayList<Student>, students.
     */
    private ArrayList<Student> loadStudents() {
        // Obtenemos los estudiantes matriculados en el curso
        List<Student> students = getStudents();
        // Actualizamos el estado de ese questionnaire para cada alumno
        setAllStudentsStates(students);
        // Actualizamos la calificacion para aquellos estudiantes que han resuelto el questionnaire desde la app movil.
        setAllStudentsGrades(students);
        return new ArrayList<>(students);
    }

    /**
     * Metodo encargado de actualizar el estado del cuestionario para cada alumno.
     * @param students, students.
     */
    private void setAllStudentsStates(List<Student> students) {
        for (Student student : students) {
            String oauth_consumer_key = questionnaire.getClaveCliente() + ":" + student.getId();
            StatusQuestionnaireRequest statusQuestionnaireRequest =
                    new StatusQuestionnaireRequest(APIRest.getApi(), oauth_consumer_key, idQuestionnarie);

            try {
                int status = statusQuestionnaireRequest.execute().get();
                if (status == RESOLVED) {
                    student.setStatus(true);
                } else if (status == UNRESOLVED) {
                    student.setStatus(false);
                } else {
                    Log.d("StudentsGradesActivity", "error in getStatus");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Metodo encargado de obtener la calificacion del cuestionario para cada alumno.
     * @param students, students.
     */
    public void setAllStudentsGrades(List<Student> students) {
        for (Student student : students) {
            String idAlumno = questionnaire.getClaveCliente() + ":" + student.getId();
            UserGradeRequest userGradeRequest = new UserGradeRequest(APIRest.getApi(), idQuestionnarie, idAlumno);
            try {
                Double grade = userGradeRequest.execute().get();
                student.setGrade(grade);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo encargado de obtener los alumnos.
     * @return ArrayList<Student>, students.
     */
    private ArrayList<Student> getStudents() {
        ArrayList<Student> retorno = new ArrayList<>();
        for (UserEnrol userEnrol : usersEnrol) {
            String userRol = userEnrol.getRoles().get(0).getShortname();
            if (userRol.equals(APIMoodle.IS_STUDENT)) {
                for (EnrolCourse enrolCourse : userEnrol.getEnrolledcourses()) {
                    int id = enrolCourse.getId();
                    if (id == idCourse) {
                        retorno.add(new Student(userEnrol.getIdUser(), userEnrol.getUsername(),
                                userEnrol.getFirstname(), userEnrol.getFullname(), userEnrol.getEmail()));
                        break;
                    }
                }

            }
        }
        return retorno;

    }

    /**
     * Metodo encargado de recuperar la informacion proporciona del anterior activity.
     */
    private void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("StudentsGradesActivity", "Intent was null");
        else {
            Log.d("StudentsGradesActivity", "Intent OK");
            position = bundle.getInt("position");
        }
    }

    /**
     * Metodo que devuelve el cuestionario.
     * @return Questionnaire, questionnaire.
     */
    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }


    /**
     * Metodo encargado de lanzar un mensaje en funcion del alumno seleccionado.
     * @param parent, parent.
     * @param view, parent.
     * @param position, parent.
     * @param id, parent.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("StudentsGradesActivity", "Cuestionarios: " + this.students.get(position));
        Toast.makeText(this, createMessage(position), Toast.LENGTH_SHORT).show();
    }

    /**
     * Metodo encargado de proporcionar el mensaje correspondiente en funcion del alumno.
     * @param position, position.
     * @return String, message.
     */
    public String createMessage(int position) {
        Student student = this.students.get(position);
        String message;
        if (student.getGrade() == NOT_INFO) {
            message = "La calificación de este alumno se encuentra ya en Moodle o no ha resuelto aún el questionnaire";
        } else {
            message = "El alumno " + student.getFullname() + " ha obtenido una calificación de " +
                    (student.getGrade() * 10);
        }
        return message;
    }

    /**
     * Metodo encargado de inflar el action bar del activity.
     * @param menu, menu.
     * @return boolean, true.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_questionary, menu);
        return true;
    }

    /**
     * Metodo encargado de proporcionar una vista del cuestionario al que se han enfrentado los alumnos.
     * @param item, item
     * @return boolean, true.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_questionary:
                goToViewTestActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Metodo encargado de direccionar a la vista del cuestionario
     */
    private void goToViewTestActivity() {
        Intent intent = new Intent(this, ViewTestActivity.class);
        intent.putExtra("idQuestionnarie", idQuestionnarie);
        startActivity(intent);
    }
}
