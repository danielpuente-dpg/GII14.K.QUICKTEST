package aplicacion.android.danielvm.quicktestandroid.models.apirest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Clase WildcardApiResponse encargada de capturar la respuesta en formato JSON a objeto.
 *
 * @author Daniel Puente Gabarri.
 */
public class WildcardApiResponse {
    @SerializedName("estado")
    private int status;
    @SerializedName("mensaje")
    private List<WildCard> wildCards;

    public WildcardApiResponse(int status, List<WildCard> wildCards) {
        this.status = status;
        this.wildCards = wildCards;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<WildCard> getWildCards() {
        return wildCards;
    }

    public void setWildCards(List<WildCard> wildCards) {
        this.wildCards = wildCards;
    }
}
