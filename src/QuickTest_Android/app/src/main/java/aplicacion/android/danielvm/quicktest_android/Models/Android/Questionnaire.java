package aplicacion.android.danielvm.quicktest_android.Models.Android;

import com.google.gson.annotations.SerializedName;

/**
 * Clase Questionnaire encargada encapsular el contenido de la respuesta JSON proporcionada
 * por el Api Rest en un objeto Java.
 *
 * @author Daniel Puente Gabarri
 */
public class Questionnaire {
    @SerializedName("idCuestionario")
    private int idQuestionnaire;
    @SerializedName("descripcion")
    private String description;
    private int imgIcon;
    @SerializedName("curso")
    private String courseName;
    @SerializedName("claveCliente")
    private String clientKey;

    public Questionnaire(int idQuestionnaire, String description, int imgIcon, String courseName, String clientKey) {
        this.idQuestionnaire = idQuestionnaire;
        this.description = description;
        this.imgIcon = imgIcon;
        this.courseName = courseName;
        this.clientKey = clientKey;
    }

    public int getIdQuestionnaire() {
        return idQuestionnaire;
    }

    public void setIdQuestionnaire(int idQuestionnaire) {
        this.idQuestionnaire = idQuestionnaire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(int imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }
}