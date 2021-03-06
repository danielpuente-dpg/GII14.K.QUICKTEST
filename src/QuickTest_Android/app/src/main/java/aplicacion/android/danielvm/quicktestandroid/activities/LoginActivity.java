package aplicacion.android.danielvm.quicktestandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import aplicacion.android.danielvm.quicktestandroid.api.APIMoodle;
import aplicacion.android.danielvm.quicktestandroid.api.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktestandroid.models.moodle.Token;
import aplicacion.android.danielvm.quicktestandroid.R;
import aplicacion.android.danielvm.quicktestandroid.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Clase LoginActivity encargada de la Logica del inicio de sesión de un usuario
 * @author Daniel Puente
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    // Elementos de la UI
    private EditText editTextName;
    private EditText editTextPassword;
    private Switch switchRemember;
    private Button btnLogIn;

    // SharedPreferences
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ocultamos el action bar
        getSupportActionBar().hide();

        // Inicializamos los elementos de la UI
        bindUI();

        // Creamos el SharePreferences para almacenar los Users
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        // Inicializamos los campos del Login si Existe un User recordado
        setCredentialsIfExists();


        // Logica del evento button al iniciar sesion
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtenememos el contenido de los EditText del Login
                String name = editTextName.getText().toString();
                String password = editTextPassword.getText().toString();

                // Obtenemos el token para los campos del Login
                initToken(name, password);

                // Guardamos los campos del Login
                saveOnPreferences(name, password);

            }
        });

    }

    /**
     * Metodo encargado de obtener el token dado un nombre y contraseña.
     * @param name, nombre introducido.
     * @param password, contraseña introducida
     */
    private void initToken(final String name, String password) {
        // Obtenemos la instancia del cliente HTTP para realizar la peticion al Webservice de Moodle
        Retrofit retrofit = APIMoodle.getApi();
        MoodleService service = retrofit.create(MoodleService.class);

        // Realizamos la llamada
        Call<Token> call = service.getToken(name, password, APIMoodle.APP);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
                int statusCode = response.code();

                Token body = response.body();
                // Si hemos obtenido un Token
                if (body.getToken() != null)
                    goToMainActivity(body.getToken(), name);
                else
                    Toast.makeText(LoginActivity.this, "Correo o contraseña erroneos", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onResponse: " + statusCode);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Error, no se puede conectar con el web service", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metoodo encargado de enlazar e inicializar los elementos de UI.
     */
    private void bindUI() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        switchRemember = (Switch) findViewById(R.id.switchRemember);
        btnLogIn = (Button) findViewById(R.id.button);
    }

    /**
     * Metodo encargardo de actualizar las credenciales.
     */
    private void setCredentialsIfExists() {
        String email = Util.getUserMailPrefs(prefs);
        String password = Util.getUserPassPrefs(prefs);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            editTextName.setText(email);
            editTextPassword.setText(password);
            switchRemember.setChecked(true);
        }
    }

    /**
     * Metodo encargano de re-direccionar al MainActivity.
     * @param token, token del usuario.
     * @param name, nombre del usuario
     */
    private void goToMainActivity(String token, String name) {
        Intent intentLogin = new Intent(this, MainActivity.class);
        intentLogin.putExtra("token", token);
        intentLogin.putExtra("name", name);
        startActivity(intentLogin);
    }

    /**
     * Metodo encargado de guardar las credenciales de usuario si el SwitchBtn se encuentra
     * activado.
     * @param email
     * @param password
     */
    private void saveOnPreferences(String email, String password) {
        if (switchRemember.isChecked()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.putString("pass", password);
            editor.apply();
        }
    }
}
