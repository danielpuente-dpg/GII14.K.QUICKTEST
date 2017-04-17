package aplicacion.android.danielvm.quicktest_android.VolleyCallbacks;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import aplicacion.android.danielvm.quicktest_android.Request.RESTService;
import aplicacion.android.danielvm.quicktest_android.Request.APIConstants;

/**
 * Created by Daniel on 26/03/2017.
 */

public class VolleyService {

    IResult mResultCallback = null;
    Context mContext;

    public VolleyService(IResult resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
    }


    public void getDataVolley() {
        RESTService restService = new RESTService(mContext);
        restService.get(APIConstants.GET_CUESTIONARIO_BY_ID_ASIGNATURA,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mResultCallback != null)
                            mResultCallback.notifySuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mResultCallback != null)
                            mResultCallback.notifyError(error);
                    }
                });
    }

    public void initToken(String email, String password) {

        String URL = APIConstants.Direcciones.HTTP_HOST + "/moodle/login/token.php?username="
                + email + "&password=" + password + "&service=moodle_mobile_app";

        RESTService restService = new RESTService(mContext);
        restService.get(URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mResultCallback != null)
                            mResultCallback.notifySuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mResultCallback != null)
                            mResultCallback.notifyError(error);
                    }
                });
    }

    /*public void postDataVolley(final String requestType, String url,JSONObject sendObj){
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            JsonObjectRequest jsonObj = new JsonObjectRequest(url,sendObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccess(requestType,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType,error);
                }
            });

            queue.add(jsonObj);

        }catch(Exception e){

        }
    }*/
}
