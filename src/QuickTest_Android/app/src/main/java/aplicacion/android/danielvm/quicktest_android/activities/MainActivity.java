package aplicacion.android.danielvm.quicktest_android.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import aplicacion.android.danielvm.quicktest_android.Adapters.PagerAdapter;
import aplicacion.android.danielvm.quicktest_android.Fragments.CuestionarioFragment;
import aplicacion.android.danielvm.quicktest_android.Models.Cuestionario;
import aplicacion.android.danielvm.quicktest_android.R;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    // Shared Preferences
    private SharedPreferences prefs;

    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        // Obtenemos el token del usuario que usara durante la sesion
        getTokenUser();

        setToolbar();
        // Preparamos el Layout del Tab
        setTabLayout();
        setViewPager();
        // Logica del Tab al realizar una accion
        setListenerTabLayout(viewPager);

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
}
