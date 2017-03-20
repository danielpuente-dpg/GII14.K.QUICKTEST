package aplicacion.android.danielvm.quicktest_android.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import aplicacion.android.danielvm.quicktest_android.R;
import aplicacion.android.danielvm.quicktest_android.http.APIConstants;
import aplicacion.android.danielvm.quicktest_android.http.SingleRequestQueue;

public class MainActivity extends AppCompatActivity {

    // Elementos de la UI
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btn;
    private EditText editTextTexto;

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
                /*new RESTService(MainActivity.this).get(APIConstants.GET_USERS,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                editTextTexto.setText(obtenerUserName(response));
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                editTextTexto.setText(error.getMessage());
                                System.out.println(error.getMessage());
                            }
                        });*/
                SingleRequestQueue.getInstance(MainActivity.this).addToRequestQueue(
                        new JsonArrayRequest(Request.Method.GET, APIConstants.GET_USERS, "",
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        try {

                                            String text = response.getJSONArray(0).getJSONObject(0).getString("id");
                                            editTextTexto.setText(text);

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
                                }
                        )
                );
            }
        });

    }


    private String obtenerToken(JSONObject response) {
        String token = null;
        try {
            token = response.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

    private String obtenerEstado(JSONObject response){
        String estado = null;
        try {
            estado = response.getString("estado");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return estado;
    }


}

