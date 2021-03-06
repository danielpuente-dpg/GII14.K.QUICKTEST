package aplicacion.android.danielvm.quicktestandroid.models.moodle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Clase Content encargada de encapsular el contenido de la respuesta JSON proporcionada
 * por el web service de Moodle en un objeto Java.
 *
 * @author Daniel Puente Gabarri.
 */

public class Content {
    private int id;
    private String name;
    private List<Module> modules;

    public Content(int id, String name, List<Module> modules) {
        this.id = id;
        this.name = name;
        this.modules = modules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    /**
     * Metodo encargado de realizar la conversion del contenido de la respuesta module,
     * implicitamente invoca a la clase Module y transforma el contenido a dicho objeto.
     *
     * @param response, response.
     * @return Module, module.
     */
    public static Module parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Module module = gson.fromJson(response, Module.class);
        return module;
    }
}
