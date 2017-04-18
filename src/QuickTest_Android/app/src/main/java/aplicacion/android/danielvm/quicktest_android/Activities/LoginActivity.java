package aplicacion.android.danielvm.quicktest_android.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import aplicacion.android.danielvm.quicktest_android.Models.Token;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Request.APIConstants;
import aplicacion.android.danielvm.quicktest_android.Request.RESTService;
import aplicacion.android.danielvm.quicktest_android.Util.Util;

public class LoginActivity extends AppCompatActivity {

    // Elementos de UI
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Switch switchRemember;
    private Switch switchConection;
    private Button btnLogIn;

    // SharedPreferences
    private SharedPreferences prefs;

    private String token;
    private RESTService restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Instanciamos e inicializamos los elemento de UI

        // Inicializamos los elementos de la UI
        bindUI();

        // Creamos el SharePreferences para almacenar los Users
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        // Inicializamos los campos del Login si Existe un User recordado
        setCredentialsIfExists();


        // Logica del evento button
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                initToken(email, password);

                if (login() && switchConection.isChecked()) {
                    goToMain();
                    saveOnPreferences(email, password);
                } else if (!switchConection.isChecked()) {
                    Toast.makeText(LoginActivity.this, "Debe activar la opción, Realizar conexión", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Correo o contraseña erroneos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        switchConection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchConection.isChecked()) {

                    btnLogIn.setVisibility(View.VISIBLE);
                    String email = editTextEmail.getText().toString();
                    String password = editTextPassword.getText().toString();

                    initToken(email, password);
                } else {
                    btnLogIn.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void bindUI() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        switchRemember = (Switch) findViewById(R.id.switchRemember);
        btnLogIn = (Button) findViewById(R.id.button);
        switchConection = (Switch) findViewById(R.id.switchConection);
    }

    private void setCredentialsIfExists() {
        String email = Util.getUserMailPrefs(prefs);
        String password = Util.getUserPassPrefs(prefs);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            editTextEmail.setText(email);
            editTextPassword.setText(password);
            switchRemember.setChecked(true);
        }
    }

    private void goToMain() {
        Intent intentLogin = new Intent(this, MainActivity.class);
        intentLogin.putExtra("token", token);
        intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentLogin);
    }

    private boolean login() {
        return (token == null) ? false : true;
    }

    private void initToken(String email, String password) {

        String URL = APIConstants.Direcciones.HTTP_HOST + "/moodle/login/token.php?username="
                + email + "&password=" + password + "&service=moodle_mobile_app";
        restService = new RESTService(LoginActivity.this);
        restService.get(URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obtenemos el token
                        token = getToken(response).getToken();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println(error.getMessage());
                    }
                });
    }

    private Token getToken(JSONObject response) {
        Gson gson = new GsonBuilder().create();
        Token token = gson.fromJson(response.toString(), Token.class);
        return token;
    }

    private void saveOnPreferences(String email, String password) {
        if (switchRemember.isChecked()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.putString("pass", password);
            editor.apply();
        }
    }
}
