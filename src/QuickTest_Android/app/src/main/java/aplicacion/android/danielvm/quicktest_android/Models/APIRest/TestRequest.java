package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Clase TestRequest encargada de enviar al API Rest el contenido de un cuestionario.
 *
 * @author Daniel Puente Gabarri.
 */

public class TestRequest {

    private int idCuestionario;
    private String idAlumno;
    private String nombreAlu;
    private String apeAlu;
    private List<Result> respuestas;

    public TestRequest(int idCuestionario, String idAlumno, String nombreAlu, String apeAlu, List<Result> respuestas) {
        this.idCuestionario = idCuestionario;
        this.idAlumno = idAlumno;
        this.nombreAlu = nombreAlu;
        this.apeAlu = apeAlu;
        this.respuestas = respuestas;
    }

    public int getIdCuestionario() {
        return idCuestionario;
    }

    public void setIdCuestionario(int idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombreAlu() {
        return nombreAlu;
    }

    public void setNombreAlu(String nombreAlu) {
        this.nombreAlu = nombreAlu;
    }

    public String getApeAlu() {
        return apeAlu;
    }

    public void setApeAlu(String apeAlu) {
        this.apeAlu = apeAlu;
    }

    public List<Result> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Result> respuestas) {
        this.respuestas = respuestas;
    }

    /**
     * Metodo encargado de realizar la conversion del contenido de la respuesta result,
     * implicitamente invoca a la clase Result y transforma el contenido a dicho objeto.
     * @param response, response.
     * @return Result, result.
     */
    public static Result parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Result result = gson.fromJson(response, Result.class);
        return result;
    }
}
