package aplicacion.android.danielvm.quicktestandroid.models.moodle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Clase ExternalTool encargada de encapsular el contenido de la respuesta JSON proporcionada
 * por el web service de Moodle en un objeto Java.
 *
 * @author Daniel Puente Gabarri.
 */

public class ExternalTool {

    private String endpoint;
    private List<Parameter> parameters;

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

    /**
     * Metodo encargado de realizar la conversion del contenido de la respuesta parameter,
     * implicitamente invoca a la clase Parameter y transforma el contenido a dicho objeto.
     *
     * @param response, response.
     * @return Parameter, parameter.
     */
    public static Parameter parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Parameter parameter = gson.fromJson(response, Parameter.class);
        return parameter;
    }

}
