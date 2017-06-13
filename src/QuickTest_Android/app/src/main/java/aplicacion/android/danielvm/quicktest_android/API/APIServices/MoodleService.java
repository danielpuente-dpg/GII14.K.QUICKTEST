package aplicacion.android.danielvm.quicktest_android.API.APIServices;


import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Content;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.ExternalTool;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Token;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.User;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.UserEnrol;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.UserProfilesByCourse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interfaz MoodleService encargada de abstraer los metodos que realizan las peticiones
 * al web service de Moodle.
 *
 * @author Daniel Puente Gabarri.
 */

public interface MoodleService {

    /**
     * Peticion que nos permite obtener el token del usuario dado
     *
     * @param user, nombre del usuario.
     * @param pass, constraseña del usuario.
     * @param app,  app.
     * @return Token, token.
     */
    @GET("login/token.php")
    Call<Token> getToken(@Query("username") String user, @Query("password") String pass, @Query("service") String app);

    /**
     * Peticion que nos permite conocer las herramientas externas dado un toolId.
     *
     * @param token,    token del usuario que tiene permisos para manejar el web service.
     * @param function, function del web service
     * @param format,   formato de respuesta a la peticion.
     * @param id,       identificador de la herramienta externa.
     * @return ExternalTool, herramienta externa.
     */
    @GET("webservice/rest/server.php")
    Call<ExternalTool> getExternalTools(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("toolid") int id);

    /**
     * Peticion que nos permite conocer todos los cursos.
     *
     * @param token,    token del usuario que tiene permisos para manejar el web service.
     * @param function, function del web service
     * @param format,   formato de respuesta a la peticion.
     * @return List<Course>, courses.
     */
    @GET("webservice/rest/server.php")
    Call<List<Course>> getCourses(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format);

    /**
     * Peticion que nos permite conocer el contenido de un curso.
     *
     * @param token,    token del usuario que tiene permisos para manejar el web service.
     * @param function, function del web service
     * @param format,   formato de respuesta a la peticion.
     * @param id,       identificador del curso.
     * @return Content[], contenido del curso.
     */
    @GET("webservice/rest/server.php")
    Call<Content[]> getContentCourse(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("courseid") int id);

    /**
     * Peticion que nos permite conocer la informacion de un usuario.
     *
     * @param token,    token del usuario que tiene permisos para manejar el web service.
     * @param function, function del web service
     * @param format,   formato de respuesta a la peticion.
     * @param field,    username.
     * @param name,     nombre del usuario
     * @return User[], user.
     */
    @GET("webservice/rest/server.php")
    Call<User[]> getUserByField(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("field") String field, @Query("values[0]") String name);

    /**
     * Peticion que nos permite conocer los cursos en los que se encuentra matriculado
     * un usuario.
     * @param token,    token del usuario que tiene permisos para manejar el web service.
     * @param function, function del web service
     * @param format,   formato de respuesta a la peticion.
     * @param id, identificador de usuario.
     * @return Course[], courses.
     */
    @GET("webservice/rest/server.php")
    Call<Course[]> getCoursesByUserId(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("userid") int id);

    /**
     * Peticion que nos permite conocer los usuarios de un curso.
     * @param token,    token del usuario que tiene permisos para manejar el web service.
     * @param function, function del web service
     * @param format,   formato de respuesta a la peticion.
     * @param idUser, identificador de usuario.
     * @param idCourse, identificador de curso
     * @return UserProfilesByCourse[], userProfilesByCourse.
     */
    @GET("webservice/rest/server.php")
    Call<UserProfilesByCourse[]> getUserGetCourse(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("userlist[0][userid]") int idUser
            , @Query("userlist[0][courseid]") int idCourse);

    /**
     * Peticion que nos permite conocer los usuarios matriculados dado un curso.
     * @param token,    token del usuario que tiene permisos para manejar el web service.
     * @param function, function del web service
     * @param format,   formato de respuesta a la peticion.
     * @param id, identificador de curso.
     * @return
     */
    @GET("webservice/rest/server.php")
    Call<UserEnrol[]> getUsersEnrolledInCourse(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("courseid") int id);


}
