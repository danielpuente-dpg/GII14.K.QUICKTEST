package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

/**
 * Clase Token encargada de encapsular el contenido de la respuesta JSON proporcionada
 * por el web service de Moodle en un objeto Java.
 *
 * @autor Daniel Puente Gabarri.
 */

public class Token {

    private String token;
    private String privatetoken;

    public Token(String token, String privatetoken) {
        this.token = token;
        this.privatetoken = privatetoken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrivatetoken() {
        return privatetoken;
    }

    public void setPrivatetoken(String privatetoken) {
        this.privatetoken = privatetoken;
    }
}
