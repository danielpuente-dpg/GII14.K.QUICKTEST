package aplicacion.android.danielvm.quicktestandroid.models.apirest;

import com.google.gson.annotations.SerializedName;

/**
 * Clase FeedbackApiResponse encargada de capturar la respuesta en formato JSON a objeto.
 *
 * @author Daniel Puente Gabarri.
 */

public class FeedbackApiResponse {
    @SerializedName("estado")
    private int status;
    @SerializedName("mensaje")
    private FeedBack feedBack;

    public FeedbackApiResponse(int status, FeedBack feedBack) {
        this.status = status;
        this.feedBack = feedBack;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public FeedBack getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(FeedBack feedBack) {
        this.feedBack = feedBack;
    }
}
