package aplicacion.android.danielvm.quicktest_android.Models.APIRest;

/**
 * Created by Daniel on 05/06/2017.
 */

public class GradeRequest {
    private String idAlumno;
    private int idCuestionario;
    private double nota;

    public GradeRequest(String idAlumno, int idCuestionario, double nota) {
        this.idAlumno = idAlumno;
        this.idCuestionario = idCuestionario;
        this.nota = nota;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdCuestionario() {
        return idCuestionario;
    }

    public void setIdCuestionario(int idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
