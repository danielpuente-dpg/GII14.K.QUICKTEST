package aplicacion.android.danielvm.quicktest_android.Utils;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.APIRest.FeedBack;

/**
 * Created by Daniel on 07/06/2017.
 */

public class RespuestaApiFeedback {
    private int estado;
    private FeedBack mensaje;

    public RespuestaApiFeedback(int estado, FeedBack mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public FeedBack getMensaje() {
        return mensaje;
    }

    public void setMensaje(FeedBack mensaje) {
        this.mensaje = mensaje;
    }
}
