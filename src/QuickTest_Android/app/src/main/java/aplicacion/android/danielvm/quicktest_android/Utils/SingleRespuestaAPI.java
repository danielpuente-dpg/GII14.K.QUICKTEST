package aplicacion.android.danielvm.quicktest_android.Utils;

/**
 * Created by Daniel on 15/05/2017.
 */

public class SingleRespuestaAPI {

    private int estado;
    private String mensaje;

    public SingleRespuestaAPI(int estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
