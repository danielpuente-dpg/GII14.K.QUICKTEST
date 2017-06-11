package aplicacion.android.danielvm.quicktest_android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIRest;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Cuestionario;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.ExternalTool;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Module;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Role;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.User;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Requests.ContentCourseRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.CoreUserGetCourseUserRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.CourseRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.EnrolUserCourseRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.ExternalTollRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.NumberContentCourseRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.StatusQuestionaryRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.TokenUserWebServiceRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.UserFieldRequest;

public class SecondActivity extends AppCompatActivity {

    // Atributos
    private String token;
    private String name;

    private String tokenWebService;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "Asdf1234!";
    public static User user;
    private Course[] courses;
    private List<Module> modules;
    private List<Course> allCourses;
    private int numExternalTools = 0;
    public static ArrayList<Cuestionario> questionaries;
    public static HashMap<Integer, List<Cuestionario>> questionariesInACourse;
    public static HashMap<Integer, Course> coursesById;


    private String ROLE_OF_USER;
    private static final String IS_STUDENT = "student";
    private static final String IS_EDIT_TEACHER = "editingteacher";
    private static final String IS_TEACHER = "teacher";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getDataBundle();

        // Obtenemos la informacion necesaria para determinar el rol de ese usario
        getAllInfo();

        // En funcion del rol, determinamos la logica a seguir
        if (ROLE_OF_USER.equals(IS_STUDENT)) {
            goToStudentActivity();

        } else if (ROLE_OF_USER.equals(IS_TEACHER)) {
            gotToTeacherActivity();
        } else {
            // TODO
        }

    }

    private void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("SecondActivity", "Error en el paso de datos entre el LoginActivity y SecondActivity");
        else {
            Log.d("SecondActivity", "Intent OK");
            token = bundle.getString("token");
            name = bundle.getString("name");
        }
    }

    private void getAllInfo() {
        // Obtenemos el token del usuario con permisos al Web Service
        tokenWebService = getTokenWebService();
        // Obtenemos la informacion del usario logeado
        user = getUserField();
        // Obtenemos los cursos en los que se encuentra matriculado ese alumno
        courses = getEnrolUserCourse();
        // Obtenemos para cada curso el rol en el que se encuentra matriculado ese alumno.
        List<String> rolesInCourse = getRolInCourses();
        if (isStudent(rolesInCourse)) {
            ROLE_OF_USER = IS_STUDENT;
        } else if (isTeacher(rolesInCourse)) {
            ROLE_OF_USER = IS_TEACHER;
        }

        getExternalToolByCourses();

    }

    private boolean isTeacher(List<String> rolesInCourse) {
        boolean retorno = true;
        for (String type : rolesInCourse) {
            if (!(type.equals(IS_TEACHER) || type.equals(IS_EDIT_TEACHER))) {
                retorno = false;
                break;
            }
        }
        return retorno;
    }

    private boolean isStudent(List<String> rolesInCourse) {
        boolean retorno = true;
        for (String type : rolesInCourse) {
            if (!type.equals(IS_STUDENT)) {
                retorno = false;
                break;
            }
        }
        return retorno;
    }

    private void getExternalToolByCourses() {
        // Para cada curso comprobamos, para todos sus cuestionarios,
        // si es de tipo LTI, y si es asi, los añadiomos
        // Es decir, todos los cuestionarios que son e tipo lti
        modules = getCoursesLTI();

        // Obtenemos todos los cursos, sin distinciones
        allCourses = getNumberOfCourses();
        // Para cada curso, comprobamos que herramientas externas hay y cuales son de tipo lti
        // y obtenemos el numero de estas
        numExternalTools = getNumberExternalTools();

        questionaries = getExternalTools();
    }

    private String getTokenWebService() {
        String tokenWebService = null;
        TokenUserWebServiceRequest tokenUserWSRequest = new TokenUserWebServiceRequest(APIMoodle.getApi(), USERNAME, PASSWORD);
        try {
            tokenWebService = tokenUserWSRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("SecondActivity", "tokenWebService: " + tokenWebService);
        return tokenWebService;
    }

    private User getUserField() {
        User user = null;
        UserFieldRequest userFieldRequest = new UserFieldRequest(APIMoodle.getApi(), tokenWebService, name);
        try {
            user = userFieldRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("SecondActivity", "user: " + user.getUsername());
        return user;
    }

    private Course[] getEnrolUserCourse() {
        Course[] courses = null;
        EnrolUserCourseRequest enrolUserCourse = new EnrolUserCourseRequest(APIMoodle.getApi(), tokenWebService, user.getId());
        try {
            courses = enrolUserCourse.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("SecondActivity", "courses: " + courses.length + "");
        return courses;
    }

    private List<String> getRolInCourses() {
        List<String> rolesInCourse = new ArrayList<>();
        for (Course course : courses) {
            CoreUserGetCourseUserRequest coreUserGetCourseUserRequest = new CoreUserGetCourseUserRequest(APIMoodle.getApi(),
                    tokenWebService, user.getId(), course.getId());

            try {
                List<Role> roles = coreUserGetCourseUserRequest.execute().get();
                rolesInCourse.add(roles.get(0).getShortname());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return rolesInCourse;

    }

    private List<Module> getCoursesLTI() {
        List<Module> modules = new ArrayList<>();
        for (int idCourse = 0; idCourse < courses.length; idCourse++) {
            ContentCourseRequest contentCourseRequest = new ContentCourseRequest(APIMoodle.getApi(), courses[idCourse].getId(), tokenWebService);
            try {
                List<Module> currentModules = contentCourseRequest.execute().get();
                modules.addAll(currentModules);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        Log.d("SecondActivity", "modules: " + modules.size() + "");
        return modules;
    }

    private List<Course> getNumberOfCourses() {
        List<Course> allCourses = null;
        CourseRequest courseRequest = new CourseRequest(APIMoodle.getApi(), tokenWebService);
        try {
            allCourses = courseRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("SecondActivity", "allCourses: " + allCourses.size());
        return allCourses;
    }

    private int getNumberExternalTools() {
        int num = 0;
        for (int idCourse = 1; idCourse <= allCourses.size(); idCourse++) {
            NumberContentCourseRequest contentCourseRequest = new NumberContentCourseRequest(APIMoodle.getApi(), tokenWebService, idCourse);
            int cont = 0;
            try {
                cont = contentCourseRequest.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            num += cont;
        }
        Log.d("SecondActivity", "numExternalTools: " + num);
        return num;
    }

    private ArrayList<Cuestionario> getExternalTools() {
        ArrayList<Cuestionario> retorno = new ArrayList<>();
        // Instanciamos los dic
        questionariesInACourse = new HashMap<>();
        coursesById = new HashMap<>();
        for (int i = 1; i <= numExternalTools; i++) {
            ExternalTollRequest externalTollRequest = new ExternalTollRequest(APIMoodle.getApi(), tokenWebService, i);
            try {
                ExternalTool externalTool = externalTollRequest.execute().get();
                addExternalTool(retorno, externalTool);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }

    private void addExternalTool(ArrayList<Cuestionario> retorno, ExternalTool externalTool) {
        Cuestionario cuestionario;
        if (externalTool.getEndpoint().equals(APIMoodle.BASE + "_QuickTest_TFG/index.php")) {

            // Comprobamos que ese usuario tenga asignado ese cuestionario
            // Obtenemos la descripcion
            String description = externalTool.getParameters().get(10).getValue();

            for (Module module : modules) {
                if (description.equals(module.getName())) {
                    Course course = null;

                    // Obtenemos el Id cuestionario
                    int idCuestionario = Integer.parseInt(externalTool.getParameters().get(11).getValue().split("=")[1].trim());
                    String curso = externalTool.getParameters().get(9).getValue();
                    String claveCliente = externalTool.getParameters().get(3).getValue();
                    cuestionario = new Cuestionario(idCuestionario, description, R.mipmap.ic_icon_cuestionario, curso, claveCliente);
                    retorno.add(cuestionario);

                    // Creamos el curso al que corresponde ese cuestionario
                    int idCourse = Integer.parseInt(externalTool.getParameters().get(7).getValue());
                    String shortName = externalTool.getParameters().get(8).getValue();
                    String fullName = externalTool.getParameters().get(9).getValue();
                    course = new Course(idCourse, shortName, fullName);


                    // Mapas de apoyo
                    if (questionariesInACourse.containsKey(idCourse)) {
                        List<Cuestionario> lista = questionariesInACourse.get(idCourse);
                        lista.add(cuestionario);
                        questionariesInACourse.put(idCourse, lista);

                    } else {
                        List<Cuestionario> lista = new ArrayList<>();
                        lista.add(cuestionario);
                        questionariesInACourse.put(idCourse, lista);

                        coursesById.put(idCourse, course);
                    }

                    Log.d("SecondActivity", "addExternalTool: " + description);
                    break;
                }
            }
        }
    }

    private void goToStudentActivity() {
        Intent intent = new Intent(this, StudentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void gotToTeacherActivity() {
        Intent intent = new Intent(this, TeacherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
