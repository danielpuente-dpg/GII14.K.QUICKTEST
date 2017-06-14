package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

/**
 * Clase Result encargada de encapsular el contenido de un cuestionario para poder
 * enviarlo al API Rest.
 *
 * @author Daniel Puente Gabarri.
 */

public class Result {
    private int idPregunta;
    private int idRespuesta;
    private String tipoComUsado;
    private String idAlumno;

    public Result(int idPregunta, int idRespuesta, String tipoComUsado, String idAlumno) {
        this.idPregunta = idPregunta;
        this.idRespuesta = idRespuesta;
        this.tipoComUsado = tipoComUsado;
        this.idAlumno = idAlumno;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getTipoComUsado() {
        return tipoComUsado;
    }

    public void setTipoComUsado(String tipoComUsado) {
        this.tipoComUsado = tipoComUsado;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }
}
