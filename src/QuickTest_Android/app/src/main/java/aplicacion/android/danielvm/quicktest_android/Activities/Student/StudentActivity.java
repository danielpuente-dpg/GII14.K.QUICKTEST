package aplicacion.android.danielvm.quicktest_android.Activities.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import aplicacion.android.danielvm.quicktest_android.API.APIRest;
import aplicacion.android.danielvm.quicktest_android.Activities.LoginActivity;
import aplicacion.android.danielvm.quicktest_android.Activities.MainActivity;
import aplicacion.android.danielvm.quicktest_android.Fragments.CuestionarioFragment;
import aplicacion.android.danielvm.quicktest_android.Fragments.ResolvedQuestionnairesFragment;
import aplicacion.android.danielvm.quicktest_android.Fragments.ThirdFragment;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Questionnaire;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.User;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Requests.StatusQuestionaryRequest;
import aplicacion.android.danielvm.quicktest_android.Utils.Util;

/**
 * Clase StudentActivity encargada de mostrar los cuestionarios de QuickTest a los alumnos.
 *
 * @author Daniel Puente Gabarri.
 */
public class StudentActivity extends AppCompatActivity {

    // Atributos del Navigation Drawer
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    // Shared Preferences
    private SharedPreferences prefs;

    // Atributos que recuperamos del login
    public String token;
    public String name;


    private static ArrayList<Questionnaire> questionnaires;
    private static ArrayList<Questionnaire> resolvedQuestionnaires;
    private MainActivity activity = new MainActivity();


    public static User user;
    private static int idCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperamos le identificador del curso seleccionado
        getDataBundle();

        // Obtenemos la informacion del usuario
        this.user = new MainActivity().user;

        // Establecemos la preferencias
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        // Obtenemos los cuestionarios en funcion de su estado
        HashMap<Integer, ArrayList<Questionnaire>> retorno = getQuestionariesFilterByStatus();
        resolvedQuestionnaires = retorno.get(0);
        questionnaires = retorno.get(1);

        setToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navview);

        setFragmentByDefault();

        // Obtenemos la vista del Navigation Drawer y modificamos los campos
        // en funcion del usuario
        View header = navigationView.getHeaderView(0);
        TextView user = (TextView) header.findViewById(R.id.textView);
        user.setText(this.user.getFullname());

        // Evento Navigation Drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switchFragment(item);
                return true;
            }
        });

    }

    /**
     * Metodo que gestiona la logica del Navigation Drawer.
     *
     * @param item, item.
     */
    private void switchFragment(@NonNull MenuItem item) {
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
    }

    /**
     * Metodo encargado de recuperar la informacion proporcionada del activity anterior.
     */
    private void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            Log.d("StudentActivity", "Intent was null");
        else {
            Log.d("StudentActivity", "Intent OK");
            idCourse = bundle.getInt("idCourse");
        }
    }

    /**
     * Metodo encargado de filtar los cuestionarios en resueltos y no resueltos.
     *
     * @return HashMap<Integer, ArrayList<Questionnaire>>, questionnaires.
     */
    private HashMap<Integer, ArrayList<Questionnaire>> getQuestionariesFilterByStatus() {
        HashMap<Integer, ArrayList<Questionnaire>> retorno = new HashMap<>();
        retorno.put(0, new ArrayList<Questionnaire>());
        retorno.put(1, new ArrayList<Questionnaire>());
        ArrayList<Questionnaire> questionaries = getQuestionariesById(idCourse);
        for (Questionnaire c : questionaries) {

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
                        ArrayList<Questionnaire> lista = retorno.get(0);
                        lista.add(c);
                        retorno.put(0, lista);
                        // Sino esta resuelto
                    } else if (estado == 0) {
                        ArrayList<Questionnaire> lista = retorno.get(1);
                        lista.add(c);
                        retorno.put(1, lista);
                    }
                } else {
                    Log.d("StudentActivity", "calculateExternalToolsResolved: error en el resultado");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


        return retorno;
    }

    /**
     * Metodo encargado proporcionar los cuestionarios de un curso.
     *
     * @param idCourse, idCourse.
     * @return ArrayList<Questionnaire>, questionnaires.
     */
    private ArrayList<Questionnaire> getQuestionariesById(int idCourse) {
        HashMap<Integer, List<Questionnaire>> questionariesInCourse = activity.getQuestionnairesInACourse();
        Log.d("StudentActivity", "questionnairesInCourse: " + questionariesInCourse.size());
        return new ArrayList<>(questionariesInCourse.get(idCourse));
    }

    /**
     * Metodo que devuelve el usuario.
     *
     * @return User, user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Metodo encargado de cerrar sesión.
     */
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

    /**
     * Metodo encargado de establecer el fragment por defecto.
     */
    private void setFragmentByDefault() {
        changeFragment(new CuestionarioFragment(), navigationView.getMenu().getItem(0));
    }

    /**
     * Metodo encargado de cambiar a otra fragmento.
     *
     * @param fragment, fragment.
     * @param item,     item.
     */
    private void changeFragment(Fragment fragment, MenuItem item) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        // Activamos el efecto de clickado
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    /**
     * Metodo encargado de enlazar el boton del burger menu con el evento
     * del navigation drawer.
     *
     * @param item, item.
     * @return boolean, true.
     */
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

    /**
     * Metodo que proporciona los cuestionarios sin resolver.
     *
     * @return ArrayList<Questionnaire>, questionnaires.
     */
    public ArrayList<Questionnaire> getDataExternalTools() {
        return questionnaires;
    }

    /**
     * Metodo que proporciona los cuestionarios resueltos.
     *
     * @return ArrayList<Questionnaire>, resolvedQuestionnaires.
     */
    public ArrayList<Questionnaire> getDataExternalToolsResolved() {
        return resolvedQuestionnaires;
    }

    /**
     * Metodo que proporciona el identificador del curso
     * @return
     */
    public int getIdCourse(){
        return idCourse;
    }


}
