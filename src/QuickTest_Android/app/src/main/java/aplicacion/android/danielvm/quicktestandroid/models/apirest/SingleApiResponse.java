package aplicacion.android.danielvm.quicktestandroid.models.apirest;

import com.google.gson.annotations.SerializedName;

/**
 * Clase SingleApiResponse encapsular el contenido de la respuesta JSON proporcionada
 * por le Api Rest en un objeto Java.
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
