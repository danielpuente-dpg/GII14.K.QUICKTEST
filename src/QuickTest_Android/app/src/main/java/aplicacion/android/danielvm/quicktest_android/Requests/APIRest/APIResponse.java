package aplicacion.android.danielvm.quicktest_android.Requests.APIRest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Message;

/**
 * Clase APIResponse encapsular el contenido de la respuesta JSON proporcionada
 * por le Api Rest en un objeto Java..
 *
 * @autor Daniel Puente Gabarri.
 */

public class APIResponse {
    @SerializedName("estado")
    private int status;
    @SerializedName("mensaje")
    private List<Message> messages;

    public APIResponse(int status, List<Message> messages) {
        this.status = status;
        this.messages = messages;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
