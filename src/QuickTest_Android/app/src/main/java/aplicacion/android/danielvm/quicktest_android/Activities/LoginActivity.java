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

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Token;
import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.Utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    // Elementos de UI
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Switch switchRemember;
    private Button btnLogIn;

    // SharedPreferences
    private SharedPreferences prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                //
                saveOnPreferences(email, password);

            }
        });

    }

    private void initToken(final String email, String password) {
        Retrofit retrofit = APIMoodle.getApi();
        MoodleService service = retrofit.create(MoodleService.class);

        Call<Token> call = service.getToken(email, password, APIMoodle.APP);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
                Token body = response.body();
                if (body.getToken() != null)
                    goToMain(body.getToken(), email);
                else
                    Toast.makeText(LoginActivity.this, "Correo o contraseña erroneos", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error, no se puede conectar con el web service", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindUI() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        switchRemember = (Switch) findViewById(R.id.switchRemember);
        btnLogIn = (Button) findViewById(R.id.button);
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

    private void goToMain(String token, String name) {
        Intent intentLogin = new Intent(this, MainActivity.class);
        intentLogin.putExtra("token", token);
        intentLogin.putExtra("name", name);
        intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentLogin);
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
