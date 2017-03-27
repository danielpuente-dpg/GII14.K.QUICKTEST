package aplicacion.android.danielvm.quicktest_android.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.VolleyCallbacks.IResult;
import aplicacion.android.danielvm.quicktest_android.VolleyCallbacks.VolleyService;
import aplicacion.android.danielvm.quicktest_android.http.APIConstants;
import aplicacion.android.danielvm.quicktest_android.http.RESTService;
import aplicacion.android.danielvm.quicktest_android.models.User;


public class MainActivity extends AppCompatActivity {

    // Elementos de la UI
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btn;
    private EditText editTextTexto;
    private ImageButton imageButton;

    private Gson gson;
    private String token;
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciamos e inicializamos los elemento de UI

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btn = (Button) findViewById(R.id.button);
        editTextTexto = (EditText) findViewById(R.id.editTextTexto);
        imageButton = (ImageButton) findViewById(R.id.imageButtonWeb);

        // Obtenemos el token del usuario
        initToken();

        // Logica del evento button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editTextEmail.getText().toString();
                String pass = editTextPassword.getText().toString();
                String url = APIConstants.GET_USERS + token +
                        "&wsfunction=moodle_user_get_users_by_courseid&moodlewsrestformat=json&courseid=1";

                RESTService restService = new RESTService(MainActivity.this);

            }
        });

        // Logica evento buttonWeb
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = APIConstants.GET_TOKEN;
                String puerto = "10.0.2.2";
                String puerto2 = "192.168.1.24";
                String puerto3 = "192.168.56.1";
                if (url != null && !url.isEmpty()) {
                    Intent intentWeb = new Intent(Intent.ACTION_VIEW,
                            /*Uri.parse("http://" + puerto2 + "/moodle/login/token.php?username=admin&password=Asdf1234!&service=moodle_mobile_app")*/
                            Uri.parse("http://" + puerto2 + "/moodle/webservice/rest/server.php?wstoken=" + "5a4743840da1a452b03fc56a4affdb73"
                                    + "&wsfunction=moodle_user_get_users_by_courseid&moodlewsrestformat=json&courseid=1"));
                    startActivity(intentWeb);
                }
            }
        });
    }

    private void initToken() {

        RESTService restService = new RESTService(MainActivity.this);
        restService.get(APIConstants.GET_TOKEN,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obtenemos el token
                        getToken(response);
                        editTextTexto.setText(token);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        editTextTexto.setText(error.getMessage());
                        System.out.println(error.getMessage());
                    }
                });
    }

    private void getToken(JSONObject response) {
        try {
            token = response.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getUsers(String token) {
        String address = APIConstants.GET_USERS + token + "&wsfunction=moodle_user_get_users_by_courseid&moodlewsrestformat=json&courseid=1";
        RESTService service = new RESTService(MainActivity.this);
        // Obtenemos los usuarios
        service.get(address,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User[] user = processUsers(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        editTextTexto.setText(error.getMessage());
                        System.out.println(error.getMessage());
                    }
                });

    }

    private User[] processUsers(JSONObject response) {
        return gson.fromJson(response.toString(), User[].class);
    }

    private void parseUsers(JSONObject response) {
    }


}

