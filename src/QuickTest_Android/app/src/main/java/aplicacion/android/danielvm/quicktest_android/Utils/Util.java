package aplicacion.android.danielvm.quicktest_android.Utils;

import android.content.SharedPreferences;

/**
 * Created by Daniel on 04/02/2017.
 */

public class Util {

    public static void removeUserToSharedPreferences(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("email");
        editor.remove("pass");
        editor.apply();
    }

    public static String getUserMailPrefs(SharedPreferences prefs) {
        return prefs.getString("email", "");
    }

    public static String getUserPassPrefs(SharedPreferences prefs) {
        return prefs.getString("pass", "");
    }
}
