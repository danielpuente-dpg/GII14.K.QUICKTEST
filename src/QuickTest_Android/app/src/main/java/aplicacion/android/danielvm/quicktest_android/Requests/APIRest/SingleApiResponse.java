package aplicacion.android.danielvm.quicktest_android.Requests.APIRest;

import com.google.gson.annotations.SerializedName;

/**
 * Clase SingleApiResponse encargada encapsular el contenido de la respuesta JSON en un objeto.
 *
 * @author Daniel Puente Gabarri.
 */

public class SingleApiResponse {
    @SerializedName("estado")
    private int status;
    @SerializedName("mensaje")
    private String message;

    public SingleApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
