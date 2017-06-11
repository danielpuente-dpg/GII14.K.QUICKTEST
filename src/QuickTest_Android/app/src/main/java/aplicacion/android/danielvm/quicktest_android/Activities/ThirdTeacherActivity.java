package aplicacion.android.danielvm.quicktest_android.Activities;

import android.support.annotation.Nullable;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIRest;
import aplicacion.android.danielvm.quicktest_android.Adapters.CourseAdapter;
import aplicacion.android.danielvm.quicktest_android.Adapters.StudentAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.EnrolCourse;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Cuestionario;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Student;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.UserEnrol;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Requests.StatusQuestionaryRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.UserGradeRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.UsersEnrolledInCourse;

public class ThirdTeacherActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    // Elementos de la UI
    private ListView listView;

    // Elementos que forman el MENU de la UI
    private MenuItem itemListView;

    // Adaptador
    private StudentAdapter listViewAdapter;

    // Atributos
    private int position;
    private Cuestionario cuestionario;
    private int idCuestionario;
    private int idCourse;
    private String tokenWebService;
    private static final String IS_STUDENT = "student";
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

        // Obtenemos los estudiantes dado un curso y cuestionario
        this.students = loadStudents();
        Log.d("ThirdTeacherActivity", this.students.size() + "");

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

    private void enforceIconBar() {
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(cuestionario.getDescripcion());
    }

    private void getOthersData() {
        SecondTeacherActivity activity = new SecondTeacherActivity();
        cuestionario = activity.getQuestionaryByPosition(position);
        idCuestionario = cuestionario.getIdCuestionario();
        idCourse = activity.getIdCourse();
        tokenWebService = activity.getTokenWebService();
        usersEnrol = getUserEnrolsInCourse();
    }

    @Nullable
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
        Log.d("ThirdTeacherActivity", usersEnrol.length + "");
        return usersEnrol;
    }

    private ArrayList<Student> loadStudents() {
        // Obtenemos los estudiantes matriculados en el curso
        List<Student> students = getStudents();
        // Actualizamos el estado de ese cuestionario para cada alumno
        setAllStudentsStates(students);
        // Actualizamos la calificacion para aquellos estudiantes que han resuelto el cuestionario desde la app movil.
        setAllStudentsGrades(students);

        return new ArrayList<>(students);
    }

    private void setAllStudentsStates(List<Student> students) {
        for (Student student : students) {
            String oauth_consumer_key = cuestionario.getClaveCliente() + ":" + student.getId();
            StatusQuestionaryRequest statusQuestionaryRequest =
                    new StatusQuestionaryRequest(APIRest.getApi(), oauth_consumer_key, idCuestionario);

            try {
                int status = statusQuestionaryRequest.execute().get();
                if (status == RESOLVED) {
                    student.setStatus(true);
                } else if (status == UNRESOLVED) {
                    student.setStatus(false);
                } else {
                    Log.d("ThirdTeacherActivity", "error in getStatus");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }

    public void setAllStudentsGrades(List<Student> students) {
        for (Student student : students) {
            String idAlumno = cuestionario.getClaveCliente() + ":" + student.getId();
            UserGradeRequest userGradeRequest = new UserGradeRequest(APIRest.getApi(), idCuestionario, idAlumno);
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

    private ArrayList<Student> getStudents() {
        ArrayList<Student> retorno = new ArrayList<>();
        for (UserEnrol userEnrol : usersEnrol) {
            String userRol = userEnrol.getRoles().get(0).getShortname();
            if (userRol.equals(IS_STUDENT)) {
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

    private void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("ThirdTeacherActivity", "Intent was null");
        else {
            Log.d("ThirdTeacherActivity", "Intent OK");
            position = bundle.getInt("position");
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ThirdTeacherActivity", "Cuestionarios: " + this.students.get(position));
        Toast.makeText(this, createMessage(position), Toast.LENGTH_SHORT).show();
    }

    public String createMessage(int position){
        Student student = this.students.get(position);
        String message;
        if(student.getGrade() == NOT_INFO){
            message = "La calificación de este alumno se encuentra ya en Moodle o no ha resuelto aún el cuestionario";
        }else{
            message = "El alumno " + student.getFullname() + " ha obtenido una calificación de " +
                    (student.getGrade() * 10);
        }
        return message;
    }

}
