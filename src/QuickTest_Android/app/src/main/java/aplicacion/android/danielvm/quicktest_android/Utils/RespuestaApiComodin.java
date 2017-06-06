package aplicacion.android.danielvm.quicktest_android.Utils;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.APIRest.WildCard;

/**
 * Created by Daniel on 06/06/2017.
 */

public class RespuestaApiComodin {
    private int estado;
    private List<WildCard> mensaje;

    public RespuestaApiComodin(int estado, List<WildCard> mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<WildCard> getMensaje() {
        return mensaje;
    }

    public void setMensaje(List<WildCard> mensaje) {
        this.mensaje = mensaje;
    }
}
