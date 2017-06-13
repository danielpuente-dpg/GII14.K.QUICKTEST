package aplicacion.android.danielvm.quicktest_android.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import aplicacion.android.danielvm.quicktest_android.Utils.Util;

/**
 * Clase SplashActivity encargada de direccionar al activity LoginActivity si no se encuentra
 * guardado un usuario, sino direcciona a CourseActivity
 * @author Daniel Puente Gabarri
 */
public class SplashActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        Intent intentLogin = new Intent(this, LoginActivity.class);
        Intent intentSecondActivity = new Intent(this, MainActivity.class);

        if (!TextUtils.isEmpty(Util.getUserMailPrefs(prefs))
                && !TextUtils.isEmpty(Util.getUserPassPrefs(prefs))) {
            startActivity(intentSecondActivity);
        } else {
            startActivity(intentLogin);
        }
        // Finalizamos este activity
        finish();

    }
}
