package aplicacion.android.danielvm.quicktest_android.Requests.APIMoodle;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.ExternalTool;
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
            e.printStackTrace();
        }


        return externalTool;
    }
}
