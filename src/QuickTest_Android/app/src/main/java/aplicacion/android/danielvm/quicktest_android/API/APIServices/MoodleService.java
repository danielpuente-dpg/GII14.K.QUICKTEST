package aplicacion.android.danielvm.quicktest_android.API.APIServices;


import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Content;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.ExternalTool;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Token;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Daniel on 22/04/2017.
 */

public interface MoodleService {

    //login/token.php?username=admin&password=Asdf1234!&service=moodle_mobile_app
    @GET("login/token.php")
    Call<Token> getToken(@Query("username") String user, @Query("password") String pass, @Query("service") String app);

    //http://localhost/moodle/webservice/rest/server.php?wstoken=ac29bdd56eb45e7669024cccc19147fe&wsfunction=mod_lti_get_tool_launch_data&moodlewsrestformat=json&toolid=1
    @GET("webservice/rest/server.php")
    Call<ExternalTool> getExternalTools(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("toolid") int id);

    @GET("webservice/rest/server.php")
    Call<Course> getCourses(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format);

    @GET("webservice/rest/server.php")
    Call<Content> getContentCourses(@Query("wstoken") String token, @Query("wsfunction") String function
            , @Query("moodlewsrestformat") String format, @Query("courseid") int id);


}
