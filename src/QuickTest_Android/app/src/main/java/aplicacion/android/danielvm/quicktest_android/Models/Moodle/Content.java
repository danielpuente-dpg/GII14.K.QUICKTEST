package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Clase Content encargada de encapsular el contenido de la respuesta JSON proporcionada
 * por el web service de Moodle en un objeto Java.
 *
 * @autor Daniel Puente Gabarri.
 */

public class Content {
    private int id;
    private String name;
    private List<Module> modules;

    public Content() {
    }

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

    public static Module parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Module module = gson.fromJson(response, Module.class);
        return module;
    }
}
