package aplicacion.android.danielvm.quicktestandroid.utils;

import android.content.SharedPreferences;

/**
 * Clase Util encargada del manejo de las SharedPreferences.
 * @author Daniel Puente
 */
public class Util {

    /**
     * Metodo encargado vaciar las SharedPreferences.
     * @param prefs, preferences a eliminar.
     */
    public static void removeUserToSharedPreferences(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("email");
        editor.remove("pass");
        editor.apply();
    }

    /**
     * Metodo para obtener el nombre del usuario.
     * @param prefs, preferencias.
     * @return String, nombre del usuario.
     */
    public static String getUserMailPrefs(SharedPreferences prefs) {
        return prefs.getString("email", "");
    }

    /**
     * Metodo para obtener la contraseña del usuario.
     * @param prefs, preferencias.
     * @return String, contraseña del usuario.
     */
    public static String getUserPassPrefs(SharedPreferences prefs) {
        return prefs.getString("pass", "");
    }

}
