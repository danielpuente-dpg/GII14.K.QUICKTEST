package aplicacion.android.danielvm.quicktest_android.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by Daniel on 17/04/2017.
 */

public class ExternalTool {

    private String endpoint;
    private List<Parameter> parameters;

    public ExternalTool(){}

    public ExternalTool(String endpoint, List<Parameter> parameters) {
        this.endpoint = endpoint;
        this.parameters = parameters;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public static Parameter parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Parameter parameter = gson.fromJson(response, Parameter.class);
        return parameter;
    }

}
