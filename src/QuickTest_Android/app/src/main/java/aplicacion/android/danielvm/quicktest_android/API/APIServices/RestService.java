package aplicacion.android.danielvm.quicktest_android.API.APIServices;

import aplicacion.android.danielvm.quicktest_android.Models.APIRest.TestRequest;
import aplicacion.android.danielvm.quicktest_android.Requests.APIRest.APIResponse;
import aplicacion.android.danielvm.quicktest_android.Requests.APIRest.FeedbackApiResponse;
import aplicacion.android.danielvm.quicktest_android.Requests.APIRest.WildcardApiResponse;
import aplicacion.android.danielvm.quicktest_android.Requests.APIRest.SingleApiResponse;
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
     * @return APIResponse, questionnaire.
     */
    @GET("obtenerCuestionario/obtener/{idTest}")
    Call<APIResponse> getTest(@Path("idTest") String idTest);

    /**
     * Peticion que nos permite obtener el estado de un cuestionario.
     *
     * @param oauth_consumer_key, clave del usuario.
     * @param id,                 identificador del cuestionario.
     * @return SingleApiResponse, respuesta.
     */
    @GET("obtenerCuestionario/estado/{oauth_consumer_key}/{idCuestionario}")
    Call<SingleApiResponse> getStatusTest(@Path("oauth_consumer_key") String oauth_consumer_key, @Path("idCuestionario") int id);

    /**
     * Peticion que nos permite insertar un las respuesta de un cuestionario.
     *
     * @param testRequest, cuestionario resuelto.
     * @return APIResponse, respuesta.
     */
    @POST("solucionCuestionario/finalizar")
    Call<aplicacion.android.danielvm.quicktest_android.Models.APIRest.APIResponse> sendTest(@Body TestRequest testRequest);

    /**
     * Peticion que nos permite obtener la calificacion de un cuestionario resuelto desde la app.
     *
     * @param idAlumno,       identificador del alumno.
     * @param idCuestionario, identificador del cuestionario.
     * @return APIResponse, respuesta.
     */
    @GET("solucionCuestionario/obtenerNota/{idAlumno}/{idCuestionario}")
    Call<aplicacion.android.danielvm.quicktest_android.Models.APIRest.APIResponse> getGrade(@Path("idAlumno") String idAlumno, @Path("idCuestionario") int idCuestionario);

    /**
     * Peticion que nos permite obtener aquellas preguntas que tienen comodin verde
     * dado un cuestionario.
     *
     * @param idCuestionario, identificador del cuestionario.
     * @return WildcardApiResponse, respuesta.
     */
    @GET("obtenerCuestionario/obtenerComodin/verde/{idCuestionario}")
    Call<WildcardApiResponse> getGreenWildCard(@Path("idCuestionario") int idCuestionario);

    /**
     * Peticion que nos permite obtener aquellas preguntas que tienen comodin ambar.
     *
     * @param idCuestionario, identificador del cuestionario.
     * @return WildcardApiResponse, respuesta.
     */
    @GET("obtenerCuestionario/obtenerComodin/ambar/{idCuestionario}")
    Call<WildcardApiResponse> getAmberWildCard(@Path("idCuestionario") int idCuestionario);

    /**
     * Peticion que nos permite obtener la tabla con la informacion del cuestionario resuelto.
     *
     * @param idCuestionario, identificador del cuestionario.
     * @param idAlumno,       identificador del alumno.
     * @return FeedbackApiResponse, respuesta.
     */
    @GET("obtenerCuestionario/feedback/{idCuestionario}/{idAlumno}")
    Call<FeedbackApiResponse> getFeedBack(@Path("idCuestionario") int idCuestionario, @Path("idAlumno") String idAlumno);


}
