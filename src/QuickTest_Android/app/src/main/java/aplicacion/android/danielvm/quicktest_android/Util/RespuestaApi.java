package aplicacion.android.danielvm.quicktest_android.Util;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.Mensaje;

/**
 * Created by Daniel on 26/04/2017.
 */

public class RespuestaApi {
    private int estado;
    private List<Mensaje> mensaje;

    public RespuestaApi(int estado, List<Mensaje> mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<Mensaje> getMensaje() {
        return mensaje;
    }

    public void setMensaje(List<Mensaje> mensaje) {
        this.mensaje = mensaje;
    }
}
