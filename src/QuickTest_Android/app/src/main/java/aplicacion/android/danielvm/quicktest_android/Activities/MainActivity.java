package aplicacion.android.danielvm.quicktest_android.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Questionnaire;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.ExternalTool;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Module;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Role;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.User;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle.ContentCourseRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle.CoreUserGetCourseUserRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle.CourseRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle.EnrolUserCourseRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle.ExternalTollRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle.NumberContentCourseRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle.TokenUserWebServiceRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle.UserFieldRequest;
import aplicacion.android.danielvm.quicktest_android.Utils.Util;

/**
 * Clase MainActivity encarga de obtener toda la información necesaria de Moodle, esta clase
 * bloquea el hilo principal de la aplicación hasta que todas sus peticiones finalicen.
 *
 * @author Daniel Puente Gabarri
 */
public class MainActivity extends AppCompatActivity {

    // Shared Preferences
    private SharedPreferences prefs;

    // Atributos
    private String name;
    private static String tokenWebService;
    public static User user;
    private Course[] courses;
    private List<Module> modules;
    private List<Course> allCourses;
    private int numExternalTools = 0;
    public static ArrayList<Questionnaire> questionnaires;
    private static HashMap<Integer, List<Questionnaire>> questionnairesInACourse;
    private static HashMap<Integer, Course> coursesById;
    private static HashMap<Integer, Role> coursesByRol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        getAllInfo();
        goToCourseActivity();
    }

    /**
     * Método encargado de direccionar al activity CourseActivity.
     */
    private void goToCourseActivity() {
        Intent intent = new Intent(this, CourseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Metodo encargado de proporcionar el token del Web service.
     *
     * @return String, tokenWebService
     */
    public String getToken() {
        return tokenWebService;
    }

    /**
     * Metodo encargado de proporcionar cuestionarios de un curso.
     * @return HashMap<Integer, List<Questionnaire>>, questionnairesInACourse.
     */
    public HashMap<Integer, List<Questionnaire>> getQuestionnairesInACourse() {
        return questionnairesInACourse;
    }

    /**
     * Metodo encargado de proporcionar los cursos por id.
     *
     * @return HashMap<Integer, Course>, coursesById.
     */
    public HashMap<Integer, Course> getQuestionnariesById() {
        return coursesById;
    }

    /**
     * Metodo encargado de proporcionar los roles por id.
     *
     * @return HashMap<Integer, Role>, coursesByRol.
     */
    public HashMap<Integer, Role> getCoursesByRol() {
        return coursesByRol;
    }


    //** Peticiones **//

    /**
     * Metodo encargado de obtener toda la informacion necesaria.
     */
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
    }

    /**
     * Metodo encargado de obtener todos los cuestionarios de QuickTest.
     */
    private void getExternalToolByCourses() {
        // Para cada curso comprobamos, para todos sus cuestionarios,
        // si es de tipo LTI, y si es asi, los añadiomos
        // Es decir, todos los cuestionarios que son de tipo lti
        modules = getCoursesLTI();

        // Obtenemos todos los cursos, sin distinciones
        allCourses = getNumberOfCourses();
        // Para cada curso, comprobamos que herramientas externas hay y cuales son de tipo lti
        // y obtenemos el numero de estas
        numExternalTools = getNumberExternalTools();
        questionnaires = getExternalTools();
    }

    /**
     * Metodo encargado de obtener el token del web service.
     *
     * @return String, tokenWebService.
     */
    private String getTokenWebService() {
        String tokenWebService = null;
        TokenUserWebServiceRequest tokenUserWSRequest =
                new TokenUserWebServiceRequest(APIMoodle.getApi(), APIMoodle.USERNAME, APIMoodle.PASSWORD);
        try {
            tokenWebService = tokenUserWSRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("MainActivity", "tokenWebService: " + tokenWebService);
        return tokenWebService;
    }

    /**
     * Metodo encargado de obtener los datos del usuario que a iniciado sesion.
     *
     * @return User, user.
     */
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
        Log.d("MainActivity", "user: " + user.getFullname());
        return user;
    }

    /**
     * Metodo encargado de obtener los cursos en los que se encuentra matriculado el usuario.
     *
     * @return Course[], courses.
     */
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
        Log.d("MainActivity", "courses: " + courses.length + "");
        return courses;
    }

    /**
     * Metodo encargado de obtener el rol de ese usuario en cada curso en el que
     * se encuentra matriculado.
     */
    private void getRolInCourses() {
        for (Course course : courses) {
            CoreUserGetCourseUserRequest coreUserGetCourseUserRequest = new CoreUserGetCourseUserRequest(APIMoodle.getApi(),
                    tokenWebService, user.getId(), course.getId());

            try {
                List<Role> roles = coreUserGetCourseUserRequest.execute().get();
                coursesByRol.put(course.getId(), roles.get(0));
                Log.d("MainActivity", "coursesByRol: " + roles.get(0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Metodo encargado de obtener el contenido de cada curso y de devolver aquellos contenidos
     * que son de tipo LTI
     *
     * @return List<Module>, modules.
     */
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
        Log.d("MainActivity", "modules: " + modules.size());
        return modules;
    }

    /**
     * Metodo que obtiene todos los cursos.
     *
     * @return List<Course>, allCourses.
     */
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
        Log.d("MainActivity", "allCourses: " + allCourses.size());
        return allCourses;
    }

    /**
     * Metodo encargado obtener el numero de herramientas externas.
     *
     * @return int, num.
     */
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
        Log.d("MainActivity", "numExternalTools: " + num);
        return num;
    }

    /**
     * Metodo encargado obtener aquellas los cuestionarios de QuickTest entre todas las herramientas
     * externas de tipo LTI.
     *
     * @return ArrayList<Questionnaire>, retorno.
     */
    private ArrayList<Questionnaire> getExternalTools() {
        ArrayList<Questionnaire> retorno = new ArrayList<>();
        // Instanciamos los dic
        questionnairesInACourse = new HashMap<>();
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
        Log.d("MainActivity", "cuestionarios: " + retorno.size());
        return retorno;
    }

    /**
     * Metodo encargado de filtar entre todas las herramientas externas, aquellas que sean de
     * QuickTest.
     *
     * @param retorno,      para ese curso, aquellas que son de QuickTest.
     * @param externalTool, herramienta externa a comprobar.
     */
    private void addExternalTool(ArrayList<Questionnaire> retorno, ExternalTool externalTool) {
        Questionnaire questionnaire;
        if (externalTool.getEndpoint().equals(APIMoodle.BASE + "_QuickTest_TFG/index.php")) {

            // Comprobamos que ese usuario tenga asignado ese questionnaire
            // Obtenemos la descripcion
            String description = externalTool.getParameters().get(10).getValue();

            for (Module module : modules) {
                if (description.equals(module.getName())) {
                    // Creamos el questionnaire
                    questionnaire = createQuestionary(retorno, externalTool, description);

                    // Creamos el curso al que corresponde ese questionnaire
                    int idCourse = Integer.parseInt(externalTool.getParameters().get(7).getValue());
                    String shortName = externalTool.getParameters().get(8).getValue();
                    String fullName = externalTool.getParameters().get(9).getValue();
                    Course course = new Course(idCourse, shortName, fullName);

                    // Mapas de apoyo
                    createMaps(questionnaire, course, idCourse);

                    Log.d("MainActivity", "addExternalTool: " + questionnaire.getDescription());
                    break;
                }
            }
        }
    }

    /**
     * Metodo encargado de crear un cuestionario en funcion de los parametros dados.
     *
     * @param retorno,      para ese curso, aquellas que son de QuickTest.
     * @param externalTool, herramienta externa a comprobar.
     * @param description,  descripcion del cuestionario.
     * @return Questionnaire, questionary.
     */
    private Questionnaire createQuestionary(ArrayList<Questionnaire> retorno, ExternalTool externalTool, String description) {
        Questionnaire questionnaire;
        // Obtenemos el Id questionnaire
        int idQuestionary = Integer.parseInt(externalTool.getParameters().get(11).getValue().split("=")[1].trim());
        String course = externalTool.getParameters().get(9).getValue();
        String clientKey = externalTool.getParameters().get(3).getValue();
        questionnaire = new Questionnaire(idQuestionary, description, R.mipmap.ic_icon_cuestionario, course, clientKey);
        retorno.add(questionnaire);
        return questionnaire;
    }

    /**
     * Metodo encargado de añadir informacion importante a estas estructuras.
     *
     * @param questionnaire, questionnaire.
     * @param course,        course.
     * @param idCourse,      idCourse.
     */
    private void createMaps(Questionnaire questionnaire, Course course, int idCourse) {
        if (questionnairesInACourse.containsKey(idCourse)) {
            List<Questionnaire> list = questionnairesInACourse.get(idCourse);
            list.add(questionnaire);
            questionnairesInACourse.put(idCourse, list);

        } else {
            List<Questionnaire> lista = new ArrayList<>();
            lista.add(questionnaire);
            questionnairesInACourse.put(idCourse, lista);
            coursesById.put(idCourse, course);
        }
    }
}
