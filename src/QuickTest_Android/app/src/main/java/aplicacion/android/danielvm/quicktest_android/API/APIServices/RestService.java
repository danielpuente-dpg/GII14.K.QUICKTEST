package aplicacion.android.danielvm.quicktest_android.API.APIServices;

import aplicacion.android.danielvm.quicktest_android.Models.APIRest.APIResponse;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.TestRequest;
import aplicacion.android.danielvm.quicktest_android.Utils.RespuestaApi;
import aplicacion.android.danielvm.quicktest_android.Utils.RespuestaApiComodin;
import aplicacion.android.danielvm.quicktest_android.Utils.RespuestaApiFeedback;
import aplicacion.android.danielvm.quicktest_android.Utils.SingleRespuestaAPI;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Daniel on 26/04/2017.
 */

public interface RestService {
    @GET("obtenerCuestionario/obtener/{idTest}")
    Call<RespuestaApi> getTest(@Path("idTest") String idTest);

    @GET("obtenerCuestionario/estado/{oauth_consumer_key}/{idCuestionario}")
    Call<SingleRespuestaAPI> getStatusTest(@Path("oauth_consumer_key") String oauth_consumer_key, @Path("idCuestionario") int id);

    @POST("solucionCuestionario/finalizar")
    Call<APIResponse> sendTest(@Body TestRequest testRequest);

    @GET("solucionCuestionario/obtenerNota/{idAlumno}/{idCuestionario}")
    Call<APIResponse> getGrade(@Path("idAlumno") String idAlumno, @Path("idCuestionario") int idCuestionario);

    @GET("obtenerCuestionario/obtenerComodin/verde/{idCuestionario}")
    Call<RespuestaApiComodin> getGreenWildCard(@Path("idCuestionario") int idCuestionario);

    @GET("obtenerCuestionario/obtenerComodin/ambar/{idCuestionario}")
    Call<RespuestaApiComodin> getAmberWildCard(@Path("idCuestionario") int idCuestionario);

    @GET("obtenerCuestionario/feedback/{idCuestionario}/{idAlumno}")
    Call<RespuestaApiFeedback> getFeedBack(@Path("idCuestionario") int idCuestionario, @Path("idAlumno") String idAlumno);


}
