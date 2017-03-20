package aplicacion.android.danielvm.quicktest_android.http;

/**
 * Created by Daniel on 20/03/2017.
 */

public class APIConstants {

    private static final String PUERTO_HOST = "63343";

    private static final String IP = "http://10.0.2.2";

    public static final String GET_TOKEN = IP + "/moodle/login/token.php?username=admin&password=Asdf1234!&service=moodle_mobile_app";
    public static final String GET_CUESTIONARIO_BY_ID_ASIGNATURA = IP + "/_QuickTest_TFG/app/apiREST/gestionCuestionario/2";
    public static final String GET_USERS = IP + "/moodle/webservice/rest/server.php?wstoken=5a4743840da1a452b03fc56a4affdb73&wsfunction=moodle_user_get_users_by_courseid&moodlewsrestformat=json&courseid=1";


}
