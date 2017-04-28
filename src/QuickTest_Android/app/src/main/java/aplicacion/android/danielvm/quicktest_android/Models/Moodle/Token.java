package aplicacion.android.danielvm.quicktest_android.Models.Moodle;

/**
 * Created by Daniel on 17/04/2017.
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
