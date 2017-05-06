package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

/**
 * Created by Daniel on 06/05/2017.
 */

public class TestRequest {

    private int idPregunta;
    private int idRespuesta;
    private String tipoComUsado;
    private String idAlumno;

    public TestRequest(int idPregunta, int idRespuesta, String tipoComUsado, String idAlumno) {
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
