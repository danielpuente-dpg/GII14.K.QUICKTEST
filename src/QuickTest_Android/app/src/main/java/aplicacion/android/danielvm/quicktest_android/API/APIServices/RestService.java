package aplicacion.android.danielvm.quicktest_android.API.APIServices;

import aplicacion.android.danielvm.quicktest_android.Util.RespuestaApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Daniel on 26/04/2017.
 */

public interface RestService {
    @GET("obtenerCuestionario/{idTest}")
    Call<RespuestaApi> getTest(@Path("idTest") String idTest);
}
