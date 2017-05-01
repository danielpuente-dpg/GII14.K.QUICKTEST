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
import java.util.List;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Adapters.PagerAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Content;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Module;
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
    public List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        // Obtenemos el token del usuario que usara durante la sesion
        getTokenUser();
        // Obtenemos el numero de cursos
        getNumberOfCourses();
        // Obtenemos el numero de herramientas externas de tipo lti
        getNumberExternalTools();


        setToolbar();
        // Preparamos el Layout del Tab
        setTabLayout();
        setViewPager();
        // Logica del Tab al realizar una accion
        setListenerTabLayout(viewPager);

    }

    private void getNumberOfCourses() {
        CourseRequest courseRequest = new CourseRequest(APIMoodle.getApi());
        try {
            courses = courseRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("NUM_COURSES", courses.size() + "");
    }

    private void getNumberExternalTools() {
        for (int idCourse = 1; idCourse <= courses.size(); idCourse++) {
            ContentCourseRequest contentCourseRequest = new ContentCourseRequest(APIMoodle.getApi(), idCourse);
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

    // Ejecutamso en el blackground y asi no se bloquea el hilo principal
    public class CourseRequest extends AsyncTask<Void, Void, List<Course>> {

        private Retrofit retrofit;

        public CourseRequest(Retrofit retrofit) {
            this.retrofit = retrofit;
        }

        @Override
        protected List<Course> doInBackground(Void... params) {
            MoodleService service = retrofit.create(MoodleService.class);
            Call<List<Course>> call = service.getCourses(token, APIMoodle.GET_COURSES, APIMoodle.FORMAT_JSON);

            try {
                courses = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return courses;
        }
    }

    public class ContentCourseRequest extends AsyncTask<Void, Void, Integer> {

        private int contador;
        private int idCourse;
        private Retrofit retrofit;

        public ContentCourseRequest(Retrofit retrofit, int idCourse) {
            this.retrofit = retrofit;
            this.idCourse = idCourse;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            MoodleService service = retrofit.create(MoodleService.class);
            Call<Content[]> call = service.getContentCourse(token, APIMoodle.GET_CONTENT_COURSE, APIMoodle.FORMAT_JSON, idCourse);

            try {
                Content[] content = call.execute().body();
                if (content != null)
                    addContentCourse(content);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return contador;
        }

        private void addContentCourse(Content[] content) {
            for (int i = 0; i < content.length; i++) {
                if (content[i] != null) {
                    List<Module> modules = content[i].getModules();
                    for (Module module : modules) {
                        if (module.getModname().equals("lti")) {
                            incCounterExternalTool();
                        }
                    }
                }

            }
        }

        private void incCounterExternalTool() {
            ++contador;
        }
    }

}
