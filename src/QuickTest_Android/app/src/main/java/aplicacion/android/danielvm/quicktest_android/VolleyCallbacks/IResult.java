package aplicacion.android.danielvm.quicktest_android.VolleyCallbacks;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Daniel on 26/03/2017.
 */

public interface IResult {
    public void notifySuccess(JSONObject response);

    public void notifyError(VolleyError error);
}
