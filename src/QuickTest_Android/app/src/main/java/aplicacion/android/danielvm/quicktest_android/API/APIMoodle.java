package aplicacion.android.danielvm.quicktest_android.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Daniel on 26/04/2017.
 */

public class APIMoodle {
    public static final String BASE_URL = "http://192.168.56.1/moodle/";
    public static final String APP = "moodle_mobile_app";
    public static final String GET_EXTERNAL_TOOL = "mod_lti_get_tool_launch_data";
    public static final String FORMAT_JSON = "json";

    private static Retrofit retrofit = null;


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
