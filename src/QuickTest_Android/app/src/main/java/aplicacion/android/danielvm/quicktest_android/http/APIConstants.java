package aplicacion.android.danielvm.quicktest_android.http;

/**
 * Created by Daniel on 20/03/2017.
 */

public class APIConstants {

    public interface Direcciones {
        String HTTP = "http://";
        String URL_GET_TOKEN = "/moodle/login/token.php?username=admin&password=Asdf1234!&service=moodle_mobile_app";
        String URL_GET_CUESTIONARIO_POR_ID_ASIG = "/_QuickTest_TFG/app/apiREST/gestionCuestionario/2";
        String URL_GET_USUARIOS_MOODLE = "/moodle/webservice/rest/server.php?wstoken=";
        // TODO OTRAS

    }

    public interface MoodleOptions {
        String SERVICE_MOODLE_MOBILE = "moodle_mobile_app";
        String RESPONSE_FORMAT = "json";
        String FUNCTION_GET_ALL_COURSES = "moodle_course_get_courses";// core_course_get_courses
        String FUNCTION_GET_CONTACTS = "core_message_get_contacts";
        String FUNCTION_GET_USERS_FROM_COURSE = "moodle_user_get_users_by_courseid";
        // TODO OTRAS


    }

    private static final String URL_BASE = "192.168.1.24";

    public static final String GET_TOKEN = Direcciones.HTTP + URL_BASE + Direcciones.URL_GET_TOKEN;
    public static final String GET_CUESTIONARIO_BY_ID_ASIGNATURA = Direcciones.HTTP + URL_BASE
            + Direcciones.URL_GET_CUESTIONARIO_POR_ID_ASIG;
    public static final String GET_USERS = Direcciones.HTTP + URL_BASE + Direcciones.URL_GET_USUARIOS_MOODLE;


}
