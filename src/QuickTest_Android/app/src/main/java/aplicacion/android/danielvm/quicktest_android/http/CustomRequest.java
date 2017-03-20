package aplicacion.android.danielvm.quicktest_android.http;

import android.service.voice.VoiceInteractionSession;
import android.support.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Daniel on 19/03/2017.
 */

public class CustomRequest extends Request {
    // the response listener
    private Response.Listener listener;

    public CustomRequest(int method, String url, Response.Listener responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = responseListener;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        if (listener != null)
            listener.onResponse(response);
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
