package aplicacion.android.danielvm.quicktest_android.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
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
import aplicacion.android.danielvm.quicktest_android.Utils.Util;

public class SecondActivity extends AppCompatActivity {

    // Shared Preferences
    private SharedPreferences prefs;

    // Atributos
    private String name;

    private static String tokenWebService;
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
    public static HashMap<Integer, Role> coursesByRol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        // Obtenemos la informacion necesaria para determinar el rol de ese usario
        getAllInfo();

        goToCourseActivity();


    }

    private void goToCourseActivity() {
        Intent intent = new Intent(this, CourseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void getAllInfo() {
        coursesByRol = new HashMap<>();
        this.name = Util.getUserMailPrefs(prefs);
        // Obtenemos el token del usuario con permisos al Web Service
        tokenWebService = getTokenWebService();
        // Obtenemos la informacion del usario logeado
        user = getUserField();
        // Obtenemos los cursos en los que se encuentra matriculado ese alumno
        courses = getEnrolUserCourse();
        // Obtenemos para cada curso el rol en el que se encuentra matriculado ese alumno.
        getRolInCourses();
        getExternalToolByCourses();

        Log.d("SecondActivity", "coursesById: " + coursesById);
        Log.d("SecondActivity", "coursesByRol: " + coursesByRol);

    }


    public String getToken() {
        return tokenWebService;
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

    private void getRolInCourses() {
        for (Course course : courses) {
            CoreUserGetCourseUserRequest coreUserGetCourseUserRequest = new CoreUserGetCourseUserRequest(APIMoodle.getApi(),
                    tokenWebService, user.getId(), course.getId());

            try {
                List<Role> roles = coreUserGetCourseUserRequest.execute().get();
                coursesByRol.put(course.getId(), roles.get(0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

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
                    Course course;

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
}
