package aplicacion.android.danielvm.quicktest_android.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 19/03/2017.
 */

public class RESTService {

    private static final String TAG = RESTService.class.getSimpleName();

    private final Context contexto;


    public RESTService(Context contexto) {
        this.contexto = contexto;
    }

    public void get(String uri, Response.Listener<JSONObject> jsonListener,
                    Response.ErrorListener errorListener) {

        // Crear petición GET
        JsonObjectRequest peticion = new JsonObjectRequest(
                uri,
                null,
                jsonListener,
                errorListener
        );

        // Añadir petición a la pila
        SingleRequestQueue.getInstance(contexto).addToRequestQueue(peticion);
    }
}
