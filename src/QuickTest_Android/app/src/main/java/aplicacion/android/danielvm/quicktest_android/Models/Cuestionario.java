package aplicacion.android.danielvm.quicktest_android.Models;

/**
 * Created by Daniel on 23/03/2017.
 */

public class Cuestionario {

    private String idCuestionario;
    private String nombreCuestionario;
    private int imgIcon;

    public Cuestionario(String idCuestionario, String descripcion, int imgIcon) {
        this.idCuestionario = idCuestionario;
        this.nombreCuestionario = descripcion;
        this.imgIcon = imgIcon;
    }

    public String getIdCuestionario() {
        return idCuestionario;
    }

    public void setIdCuestionario(String idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    public String getNombreCuestionario() {
        return nombreCuestionario;
    }

    public void setNombreCuestionario(String nombreCuestionario) {
        this.nombreCuestionario = nombreCuestionario;
    }

    public int getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(int imgIcon) {
        this.imgIcon = imgIcon;
    }
}
