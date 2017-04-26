package aplicacion.android.danielvm.quicktest_android.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Daniel on 26/04/2017.
 */

public class APIRest {
    public static final String BASE = "http://10.0.2.2/";
    public static final String BASE_URL = BASE + "_QuickTest_TFG/app/apiRest/";


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
