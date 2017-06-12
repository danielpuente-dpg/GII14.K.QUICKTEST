package aplicacion.android.danielvm.quicktest_android.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import aplicacion.android.danielvm.quicktest_android.Utils.Util;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        Intent intentLogin = new Intent(this, LoginActivity.class);
        Intent intentSecondActivity = new Intent(this, SecondActivity.class);

        if (!TextUtils.isEmpty(Util.getUserMailPrefs(prefs))
                && !TextUtils.isEmpty(Util.getUserPassPrefs(prefs))) {
            startActivity(intentSecondActivity);
        } else {
            startActivity(intentLogin);
        }
        // Eliminamos la instancia de este activity
        finish();

    }
}
