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
 * Interfaz RestService encargada de abstraer los metodos que realizan las peticiones
 * al apiRest de QuickTest.
 *
 * @author Daniel Puente Gabarri.
 */

public interface RestService {

    /**
     * Peticion que nos permite obtener un cuestionario.
     *
     * @param idTest, idQuestionnaire.
     * @return RespuestaApi, questionnaire.
     */
    @GET("obtenerCuestionario/obtener/{idTest}")
    Call<RespuestaApi> getTest(@Path("idTest") String idTest);

    /**
     * Peticion que nos permite obtener el estado de un cuestionario.
     *
     * @param oauth_consumer_key, clave del usuario.
     * @param id,                 identificador del cuestionario.
     * @return SingleRespuestaAPI, respuesta.
     */
    @GET("obtenerCuestionario/estado/{oauth_consumer_key}/{idCuestionario}")
    Call<SingleRespuestaAPI> getStatusTest(@Path("oauth_consumer_key") String oauth_consumer_key, @Path("idCuestionario") int id);

    /**
     * Peticion que nos permite insertar un las respuesta de un cuestionario.
     *
     * @param testRequest, cuestionario resuelto.
     * @return APIResponse, respuesta.
     */
    @POST("solucionCuestionario/finalizar")
    Call<APIResponse> sendTest(@Body TestRequest testRequest);

    /**
     * Peticion que nos permite obtener la calificacion de un cuestionario resuelto desde la app.
     *
     * @param idAlumno,       identificador del alumno.
     * @param idCuestionario, identificador del cuestionario.
     * @return APIResponse, respuesta.
     */
    @GET("solucionCuestionario/obtenerNota/{idAlumno}/{idCuestionario}")
    Call<APIResponse> getGrade(@Path("idAlumno") String idAlumno, @Path("idCuestionario") int idCuestionario);

    /**
     * Peticion que nos permite obtener aquellas preguntas que tienen comodin verde
     * dado un cuestionario.
     *
     * @param idCuestionario, identificador del cuestionario.
     * @return RespuestaApiComodin, respuesta.
     */
    @GET("obtenerCuestionario/obtenerComodin/verde/{idCuestionario}")
    Call<RespuestaApiComodin> getGreenWildCard(@Path("idCuestionario") int idCuestionario);

    /**
     * Peticion que nos permite obtener aquellas preguntas que tienen comodin ambar.
     *
     * @param idCuestionario, identificador del cuestionario.
     * @return RespuestaApiComodin, respuesta.
     */
    @GET("obtenerCuestionario/obtenerComodin/ambar/{idCuestionario}")
    Call<RespuestaApiComodin> getAmberWildCard(@Path("idCuestionario") int idCuestionario);

    /**
     * Peticion que nos permite obtener la tabla con la informacion del cuestionario resuelto.
     *
     * @param idCuestionario, identificador del cuestionario.
     * @param idAlumno,       identificador del alumno.
     * @return RespuestaApiFeedback, respuesta.
     */
    @GET("obtenerCuestionario/feedback/{idCuestionario}/{idAlumno}")
    Call<RespuestaApiFeedback> getFeedBack(@Path("idCuestionario") int idCuestionario, @Path("idAlumno") String idAlumno);


}
