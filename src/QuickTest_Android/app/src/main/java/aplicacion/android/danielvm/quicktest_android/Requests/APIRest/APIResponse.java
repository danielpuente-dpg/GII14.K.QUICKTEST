package aplicacion.android.danielvm.quicktest_android.Requests.APIRest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Mensaje;

/**
 * Clase
 */

public class APIResponse {
    @SerializedName("estado")
    private int status;
    @SerializedName("mensaje")
    private List<Mensaje> messages;

    public APIResponse(int status, List<Mensaje> messages) {
        this.status = status;
        this.messages = messages;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Mensaje> getMessages() {
        return messages;
    }

    public void setMessages(List<Mensaje> messages) {
        this.messages = messages;
    }
}
