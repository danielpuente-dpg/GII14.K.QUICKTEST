package aplicacion.android.danielvm.quicktest_android.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Adapters.PagerAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Content;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Module;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Token;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.User;
import aplicacion.android.danielvm.quicktest_android.R;
import retrofit2.Call;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    // Shared Preferences
    private SharedPreferences prefs;

    public static String token;
    public static int NUM_EXTERNAL_TOOLS = 0;
    public static Course[] courses;
    public static List<Course> AllCourses;
    public static List<Module> modules = new ArrayList<>();

    public static String USERNAME = "admin";
    public static String PASSWORD = "Asdf1234!";
    public static String TOKEN_WS;
    public static String NAME;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        //////////////////////////////////////////////////
        // Obtenemos el token del usuario que usara durante la sesion
        getTokenUser();
        // Obtenemos el nombre del usuario
        getNameUser();
        //////////////////////////////////////////////////

        // Obtenemos el token del usuario encargado de interactuar con el WebServices
        getTokenWS();
        // Obtenemos la informacion del usuario
        getUserField();
        // Obtenemos los cursos en los que ese usuario se encuentra matriculado
        getEnrolUserCourse(user.getId());

        // Obtenemos los cursos de tipo lti
        getCoursesLTI();


        // Obtenemos todos los cursos
        getNumberOfCourses();

        Log.d("Modules2", modules.size() + "");
        // Obtenemos el numero de herramientas externas de tipo lti
        getNumberExternalTools();
        Log.d("Modules3", modules.size() + "");


        setToolbar();
        // Preparamos el Layout del Tab
        setTabLayout();
        setViewPager();
        // Logica del Tab al realizar una accion
        setListenerTabLayout(viewPager);

    }

    private void getTokenWS() {
        TokenUserWSRequest tokenUserWSRequest = new TokenUserWSRequest(APIMoodle.getApi());
        try {
            TOKEN_WS = tokenUserWSRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("TOKEN_WS", TOKEN_WS);

    }


    private void getUserField() {
        UserFieldRequest userFieldRequest = new UserFieldRequest(APIMoodle.getApi());
        try {
            user = userFieldRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("USER", user.getUsername());
    }

    private void getEnrolUserCourse(int userId) {
        EnrolUserCourse enrolUserCourse = new EnrolUserCourse(APIMoodle.getApi(), userId);
        try {
            courses = enrolUserCourse.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("COURSE", courses.length + "");
    }

    private void getCoursesLTI() {
        //modules = new ArrayList<>();
        for (int idCourse = 0; idCourse < courses.length; idCourse++) {
            ContentCourseRequest contentCourseRequest = new ContentCourseRequest(APIMoodle.getApi(), courses[idCourse].getId());
            try {
                List<Module> currentModules = contentCourseRequest.execute().get();
                modules.addAll(currentModules);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        Log.d("Modules", modules.size() + "");
    }


    private void getNumberOfCourses() {
        CourseRequest courseRequest = new CourseRequest(APIMoodle.getApi());
        try {
            AllCourses = courseRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("AllCourses", AllCourses.size() + "");
    }

    private void getNumberExternalTools() {
        for (int idCourse = 1; idCourse <= AllCourses.size(); idCourse++) {
            NumberContentCourseRequest contentCourseRequest = new NumberContentCourseRequest(APIMoodle.getApi(), idCourse);
            int cont = 0;
            try {
                cont = contentCourseRequest.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            NUM_EXTERNAL_TOOLS += cont;
        }
        Log.d("NUM_EXTERNAL_TOOLS", NUM_EXTERNAL_TOOLS + "");
    }


    private void getTokenUser() {
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        token = bundle.getString("token");
        Log.d("token", token);
    }

    private void getNameUser() {
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        NAME = bundle.getString("name");
        Log.d("NAME", NAME);
    }

    private void setToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    private void setTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_icon_tag_cuestionario));
        tabLayout.addTab(tabLayout.newTab().setText("Cuestionarios"));
        tabLayout.addTab(tabLayout.newTab().setText("Resultados"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void setViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        // Cada vez que cambiemos de Tabs, el viewPager (encargado de los fragments )tambien lo cambie
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setListenerTabLayout(final ViewPager viewPager) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // Al seleccionar el Tab
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            // Cuando el Tab activo deja de estarlo
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            // Cuando seleccionamos el mismo tab que esta activo
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /// Clases internas para realizar peticiones de manera sincrona en el BackGround.
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    // Ejecutamos en el blackground y asi no se bloquea el hilo principal

    public class TokenUserWSRequest extends AsyncTask<Void, Void, String> {
        private Retrofit retrofit;
        private Token token;

        public TokenUserWSRequest(Retrofit retrofit) {
            this.retrofit = retrofit;

        }

        @Override
        protected String doInBackground(Void... params) {

            MoodleService service = retrofit.create(MoodleService.class);
            Call<Token> call = service.getToken(USERNAME, PASSWORD, APIMoodle.APP);


            try {
                token = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return token.getToken();
        }
    }

    public class UserFieldRequest extends AsyncTask<Void, Void, User> {

        private Retrofit retrofit;
        private User[] user;

        public UserFieldRequest(Retrofit retrofit) {
            this.retrofit = retrofit;

        }

        @Override
        protected User doInBackground(Void... params) {

            MoodleService service = retrofit.create(MoodleService.class);
            Call<User[]> call = service.getUserByField(TOKEN_WS, APIMoodle.GET_USER_BY_FIELD, APIMoodle.FORMAT_JSON, "username", NAME);

            try {
                user = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return user[0];
        }
    }

    public class EnrolUserCourse extends AsyncTask<Void, Void, Course[]> {

        private Retrofit retrofit;
        private Course[] course;
        private int userId;

        public EnrolUserCourse(Retrofit retrofit, int userId) {
            this.retrofit = retrofit;
            this.userId = userId;
        }

        @Override
        protected Course[] doInBackground(Void... params) {

            MoodleService service = retrofit.create(MoodleService.class);
            Call<Course[]> call = service.getCoursesByUserId(TOKEN_WS, APIMoodle.GET_COURSES_BY_USER_ID, APIMoodle.FORMAT_JSON, userId);

            try {
                course = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return course;
        }
    }

    public class CourseRequest extends AsyncTask<Void, Void, List<Course>> {

        private Retrofit retrofit;
        private List<Course> courses;

        public CourseRequest(Retrofit retrofit) {
            this.retrofit = retrofit;
        }

        @Override
        protected List<Course> doInBackground(Void... params) {
            MoodleService service = retrofit.create(MoodleService.class);
            Call<List<Course>> call = service.getCourses(TOKEN_WS, APIMoodle.GET_COURSES, APIMoodle.FORMAT_JSON);

            try {
                courses = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return courses;
        }
    }

    public class ContentCourseRequest extends AsyncTask<Void, Void, List<Module>> {

        private int idCourse;
        private List<Module> modules;
        private Retrofit retrofit;

        public ContentCourseRequest(Retrofit retrofit, int idCourse) {
            this.retrofit = retrofit;
            this.idCourse = idCourse;
            modules = new ArrayList<>();
        }

        @Override
        protected List<Module> doInBackground(Void... params) {
            MoodleService service = retrofit.create(MoodleService.class);
            Call<Content[]> call = service.getContentCourse(TOKEN_WS, APIMoodle.GET_CONTENT_COURSE, APIMoodle.FORMAT_JSON, idCourse);

            try {
                Content[] content = call.execute().body();
                if (content != null)
                    addContentCourse(content);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return modules;
        }

        private void addContentCourse(Content[] content) {
            for (int i = 0; i < content.length; i++) {
                if (content[i] != null) {
                    List<Module> modules = content[i].getModules();
                    for (Module module : modules) {
                        if (module.getModname().equals("lti")) {
                            addExternalTool(module);
                        }
                    }
                }

            }
        }

        private void addExternalTool(Module module) {
            modules.add(module);
        }
    }

    public class NumberContentCourseRequest extends AsyncTask<Void, Void, Integer> {

        private int idCourse;
        private int cont = 0;
        private Retrofit retrofit;

        public NumberContentCourseRequest(Retrofit retrofit, int idCourse) {
            this.retrofit = retrofit;
            this.idCourse = idCourse;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            MoodleService service = retrofit.create(MoodleService.class);
            Call<Content[]> call = service.getContentCourse(TOKEN_WS, APIMoodle.GET_CONTENT_COURSE, APIMoodle.FORMAT_JSON, idCourse);

            try {
                Content[] content = call.execute().body();
                if (content != null)
                    addContentCourse(content);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return cont;
        }

        private void addContentCourse(Content[] content) {
            for (int i = 0; i < content.length; i++) {
                if (content[i] != null) {
                    List<Module> modules = content[i].getModules();
                    for (Module module : modules) {
                        if (module.getModname().equals("lti")) {
                            incNumberExternalTools();
                        }
                    }
                }

            }
        }
        private void incNumberExternalTools(){
            ++cont;
        }
    }



}
