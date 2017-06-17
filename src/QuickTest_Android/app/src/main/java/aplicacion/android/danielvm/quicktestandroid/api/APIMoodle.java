package aplicacion.android.danielvm.quicktestandroid.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase APIMoodle encargada de proporcionar una unica instancia para poder comunicarse
 * con el web service de Moodle
 *
 * @author Daniel Puente Gabarri.
 */

public class APIMoodle {

    public static final String BASE = "http://192.168.1.33/";
    public static final String BASE_URL = BASE + "moodle/";
    public static final String APP = "moodle_mobile_app";

    // Funciones del web service
    public static final String GET_EXTERNAL_TOOL = "mod_lti_get_tool_launch_data";
    public static final String FORMAT_JSON = "json";
    public static final String GET_COURSES = "core_course_get_courses";
    public static final String GET_CONTENT_COURSE = "core_course_get_contents";
    public static final String GET_USER_BY_FIELD = "core_user_get_users_by_field";
    public static final String GET_COURSES_BY_USER_ID = "core_enrol_get_users_courses";
    public static final String GET_PROFILES_USER_BY_USER = "core_user_get_course_user_profiles";
    public static final String GET_USERS_ENROLLED_IN_COURSE = "core_enrol_get_enrolled_users";

    // Campos del usuario que maneja el web servie
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "Asdf1234!";

    // tipos de roles utilizados
    public static final String IS_STUDENT = "student";
    public static final String IS_EDIT_TEACHER = "editingteacher";
    public static final String IS_TEACHER = "teacher";

    private static Retrofit retrofit = null;

    private APIMoodle() {
    }

    /**
     * Metodo encargado de proporcionarnos la instancia para comunicarnos con el web service.
     *
     * @return Retrofit, retrofit.
     */
    public static Retrofit getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
