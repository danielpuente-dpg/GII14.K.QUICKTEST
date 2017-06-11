package aplicacion.android.danielvm.quicktest_android.API.APIServices;


import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.APIRest.EnrolCourse;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Content;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.ExternalTool;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Role;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Token;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.User;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.UserEnrol;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.UserProfilesByCourse;
import aplicacion.android.danielvm.quicktest_android.Requests.EnrolUserCourseRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Daniel on 22/04/2017.
 */

public interface MoodleService {

    /**
     *
     * @param user
     * @param pass
     * @param app
     * @return
     */
    @GET("login/token.php")
    Call<Token> getToken(@Query("username") String user, @Query("password") String pass, @Query("service") String app);

    /**
     *
     * @param token
     * @param function
     * @param format
     * @param id
     * @return
     */
    @GET("webservice/rest/server.php")
    Call<ExternalTool> getExternalTools(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("toolid") int id);

    /**
     *
     * @param token
     * @param function
     * @param format
     * @return
     */
    @GET("webservice/rest/server.php")
    Call<List<Course>> getCourses(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format);

    /**
     *
     * @param token
     * @param function
     * @param format
     * @param id
     * @return
     */
    @GET("webservice/rest/server.php")
    Call<Content[]> getContentCourse(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("courseid") int id);

    @GET("webservice/rest/server.php")
    Call<User[]> getUserByField(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("field") String field, @Query("values[0]") String name);

    @GET("webservice/rest/server.php")
    Call<Course[]> getCoursesByUserId(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("userid") int id);


    @GET("webservice/rest/server.php")
    Call<UserProfilesByCourse[]> getUserGetCourse(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("userlist[0][userid]") int idUser
            , @Query("userlist[0][courseid]") int idCourse);


    @GET("webservice/rest/server.php")
    Call<UserEnrol[]> getUsersEnrolledInCourse(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("courseid") int id);


}
