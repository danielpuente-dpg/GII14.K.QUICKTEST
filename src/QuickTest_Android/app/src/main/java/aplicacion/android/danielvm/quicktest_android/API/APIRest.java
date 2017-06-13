package aplicacion.android.danielvm.quicktest_android.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase APIRest encargada de proporcionar una unica instancia para poder comunicarse
 * con el Api rest de QuickTest
 *
 * @author Daniel Puente Gabarri.
 */

public class APIRest {

    //public static final String BASE = "http://10.0.2.2/";
    public static final String BASE = "http://192.168.1.37/";
    public static final String BASE_URL = BASE + "_QuickTest_TFG/app/apiRest/";

    private static Retrofit retrofit = null;

    /**
     * Metodo encargado de proporcionar la isntancia para poder comunicarse con el
     * Api rest de QuickTest.
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
