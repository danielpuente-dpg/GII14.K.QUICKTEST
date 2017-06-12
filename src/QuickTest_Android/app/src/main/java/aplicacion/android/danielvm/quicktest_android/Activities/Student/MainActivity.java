package aplicacion.android.danielvm.quicktest_android.Activities.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIRest;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;

import aplicacion.android.danielvm.quicktest_android.API.APIServices.RestService;
import aplicacion.android.danielvm.quicktest_android.Activities.LoginActivity;
import aplicacion.android.danielvm.quicktest_android.Activities.SecondActivity;
import aplicacion.android.danielvm.quicktest_android.Fragments.CuestionarioFragment;
import aplicacion.android.danielvm.quicktest_android.Fragments.ResolvedQuestionnairesFragment;
import aplicacion.android.danielvm.quicktest_android.Fragments.ThirdFragment;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.APIResponse;
import aplicacion.android.danielvm.quicktest_android.Models.APIRest.GradeRequest;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Cuestionario;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Content;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.ExternalTool;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Module;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Token;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.User;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Requests.StatusQuestionaryRequest;
import aplicacion.android.danielvm.quicktest_android.Utils.SingleRespuestaAPI;
import aplicacion.android.danielvm.quicktest_android.Utils.Util;
import retrofit2.Call;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    // Atributos del Navigation Drawer
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    // Shared Preferences
    private SharedPreferences prefs;

    // Atributos que recuperamos del login
    public String token;
    public String name;


    public ArrayList<Cuestionario> questionaries;
    public ArrayList<Cuestionario> resolvedQuestionnaires;



    public static User user;
    public int idCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperamos le identificador del curso seleccionado
        getDataBundle();

        // Obtenemos la informacion del usuario
        this.user = new SecondActivity().user;

        // Establecemos la preferencias
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        // Obtenemos los cuestionarios en funcion de su estado
        HashMap<Integer, ArrayList<Cuestionario>> retorno = getQuestionariesFilterByStatus();
        resolvedQuestionnaires = retorno.get(0);
        questionaries = retorno.get(1);

        setToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navview);

        setFragmentByDefault();

        // Obtenemos la vista del Navigation Drawer y modificamos los campos
        // en funcion del usuario
        View header = navigationView.getHeaderView(0);
        TextView user = (TextView) header.findViewById(R.id.textView);
        user.setText(this.user.getFirstname());

        // Evento Navigation Drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.menu_courses:
                        fragment = new CuestionarioFragment();
                        fragmentTransaction = true;
                        break;

                    case R.id.menu_courses_end:
                        fragment = new ResolvedQuestionnairesFragment();
                        fragmentTransaction = true;
                        break;

                    case R.id.menu_info:
                        fragment = new ThirdFragment();
                        fragmentTransaction = true;
                        break;

                    case R.id.menu_log_out:
                        logOut();
                        break;

                    case R.id.menu_forget_and_log_out:
                        Util.removeUserToSharedPreferences(prefs);
                        logOut();
                        break;
                }

                if (fragmentTransaction) {
                    changeFragment(fragment, item);
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });

    }

    private void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("MainActivity", "Intent was null");
        else {
            Log.d("MainActivity", "Intent OK");
            idCourse = bundle.getInt("idCourse");
        }
    }

    private HashMap<Integer, ArrayList<Cuestionario>> getQuestionariesFilterByStatus() {
        HashMap<Integer, ArrayList<Cuestionario>> retorno = new HashMap<>();
        retorno.put(0, new ArrayList<Cuestionario>());
        retorno.put(1, new ArrayList<Cuestionario>());
        ArrayList<Cuestionario> questionaries = getQuestionariesById(idCourse);
        for (Cuestionario c : questionaries) {

            // Obtenemos la info de la streamQuery
            String oauth_consumer_key = c.getClaveCliente() + ":" + user.getId();
            int idCuestionario = c.getIdCuestionario();

            // Realizamos la peticion al APIRest
            StatusQuestionaryRequest statusQuestionaryRequest =
                    new StatusQuestionaryRequest(APIRest.getApi(), oauth_consumer_key, idCuestionario);

            try {
                int estado = statusQuestionaryRequest.execute().get();
                if (estado != -1) {
                    // Si esta resuelto
                    if (estado == 1) {
                        ArrayList<Cuestionario> lista = retorno.get(0);
                        lista.add(c);
                        retorno.put(0, lista);
                        // Sino esta resuelto
                    } else if (estado == 0) {
                        ArrayList<Cuestionario> lista = retorno.get(1);
                        lista.add(c);
                        retorno.put(1, lista);
                    }
                } else {
                    Log.d("SecondActivity", "calculateExternalToolsResolved: error en el resultado");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


        return retorno;
    }
    private ArrayList<Cuestionario> getQuestionariesById(int idCourse){
        HashMap<Integer, List<Cuestionario>> questionariesInCourse = new SecondActivity().questionariesInACourse;
        return new ArrayList<>(questionariesInCourse.get(idCourse));
    }

    public User getUser() {
        return user;
    }

    private void logOut() {
        Intent intentLogin = new Intent(this, LoginActivity.class);

        startActivity(intentLogin);
    }

    /**
     * Metodo encargardo de la configuracion del toolbar y de añadir el icono
     * del Navigation Drawer.
     */
    private void setToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setFragmentByDefault() {
        changeFragment(new CuestionarioFragment(), navigationView.getMenu().getItem(0));
    }

    private void changeFragment(Fragment fragment, MenuItem item) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        // Activamos el efecto de clickado
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }


    // Metodo encargado de enlazar el boton del burger menu con el evento
    // del navigation drawer.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Abrimos el menu lateral
                // La opcion start, esque lo tenemos a la parte izquierda
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Cuestionario> getDataExternalTools() {
        return questionaries;
    }

    public ArrayList<Cuestionario> getDataExternalToolsResolved() {
        return resolvedQuestionnaires;
    }



}
