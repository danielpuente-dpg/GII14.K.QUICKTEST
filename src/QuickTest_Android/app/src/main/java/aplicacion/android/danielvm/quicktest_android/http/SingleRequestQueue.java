package aplicacion.android.danielvm.quicktest_android.http;

import android.content.Context;
import android.service.voice.VoiceInteractionSession;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Daniel on 19/03/2017.
 */

public class SingleRequestQueue {
    private static SingleRequestQueue instance;
    private RequestQueue requestQueue;
    private Context context;

    private SingleRequestQueue(Context context){
        this.context = context;
    }

    public static synchronized SingleRequestQueue getInstance(Context context){
        if(instance == null)
            instance = new SingleRequestQueue(context);
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
