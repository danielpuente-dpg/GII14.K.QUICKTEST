package aplicacion.android.danielvm.quicktestandroid.requests.apimoodle;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import aplicacion.android.danielvm.quicktestandroid.api.APIMoodle;
import aplicacion.android.danielvm.quicktestandroid.api.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktestandroid.models.moodle.ExternalTool;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Clase ExternalTollRequest encargada de obtener una herramienta externa.
 *
 * @author Daniel Puente Gabarri.
 */

public class ExternalTollRequest extends AsyncTask<Void, Void, ExternalTool> {

    private Retrofit retrofit;
    private String token;
    private int toolId;

    /**
     * Constructor de la clase.
     *
     * @param retrofit, retrofit.
     * @param token,    token del web service.
     * @param toolId,   identificador de la herramienta externa.
     */
    public ExternalTollRequest(Retrofit retrofit, String token, int toolId) {
        this.retrofit = retrofit;
        this.token = token;
        this.toolId = toolId;
    }

    /**
     * Metodo encargado de realizar la peticion de manera sincrona, bloqueando el hilo principal.
     *
     * @param params, params.
     * @return ExternalTool, externalTool.
     */
    @Override
    protected ExternalTool doInBackground(Void... params) {
        ExternalTool externalTool = null;

        MoodleService service = retrofit.create(MoodleService.class);
        Call<ExternalTool> call = service.getExternalTools(token, APIMoodle.GET_EXTERNAL_TOOL, APIMoodle.FORMAT_JSON, toolId);

        try {
            externalTool = call.execute().body();
        } catch (IOException e) {
            Log.d("ExternalTollRequest", e.getMessage());
        }


        return externalTool;
    }
}
