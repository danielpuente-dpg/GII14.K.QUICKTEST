package aplicacion.android.danielvm.quicktest_android;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import aplicacion.android.danielvm.quicktest_android.http.RESTService;
import aplicacion.android.danielvm.quicktest_android.http.SingleRequestQueue;

public class MainActivity extends AppCompatActivity {

    // Elementos de la UI
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btn;
    private EditText editTextTexto;

    private static final String URL = "http://10.0.2.2/moodle/login/token.php?username=admin&password=Asdf1234!&service=moodle_mobile_app";
    private static final String URL2 = "http://10.0.2.2/_QuickTest_TFG/app/apiREST/gestionCuestionario/2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btn = (Button) findViewById(R.id.button);
        editTextTexto = (EditText) findViewById(R.id.editTextTexto);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RESTService(MainActivity.this).get(URL,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    editTextTexto.setText(response.getString("token"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
        });

    }

    private String parseJson(JSONObject response) {
        String token = null;
        try {
            String object = response.getString("estado");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

}

