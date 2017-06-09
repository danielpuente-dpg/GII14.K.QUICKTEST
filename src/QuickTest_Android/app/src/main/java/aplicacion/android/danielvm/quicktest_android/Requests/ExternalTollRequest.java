package aplicacion.android.danielvm.quicktest_android.Requests;

import android.os.AsyncTask;

import java.io.IOException;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.ExternalTool;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Daniel on 09/06/2017.
 */

public class ExternalTollRequest extends AsyncTask<Void, Void, ExternalTool> {

    private Retrofit retrofit;
    private String token;
    private int toolId;

    public ExternalTollRequest(Retrofit retrofit, String token, int toolId) {
        this.retrofit = retrofit;
        this.token = token;
        this.toolId = toolId;
    }

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
