package aplicacion.android.danielvm.quicktest_android.VolleyCallbacks;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import aplicacion.android.danielvm.quicktest_android.Fragments.CuestionarioFragment;
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


    public void getAndAddCuestionarios(String token, int count, int NUM_EXTERNAL_TOOLS) {
        RESTService restService = new RESTService(mContext);

        for(; count < NUM_EXTERNAL_TOOLS; count++) {
            String url = APIConstants.GET_EXTERNAL_TOOL + token +
                    "&wsfunction=mod_lti_get_tool_launch_data&moodlewsrestformat=json&moodlewsrestformat=json&toolid=" + count;

            restService.get(url,
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
    }
}
