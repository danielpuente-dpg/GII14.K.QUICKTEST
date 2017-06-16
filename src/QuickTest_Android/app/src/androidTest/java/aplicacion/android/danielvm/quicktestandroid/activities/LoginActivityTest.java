package aplicacion.android.danielvm.quicktestandroid.activities;


import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import aplicacion.android.danielvm.quicktestandroid.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    public static final String mName = "profesor";
    public static final String mPass = "Asdf1234!";
    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivity = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest() {
        onView(withId(R.id.editTextName))
                .perform(clearText())
                .perform(typeText(mName), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.editTextPassword))
                .perform(clearText())
                .perform(typeText(mPass), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.switchRemember))
                .perform(click());

        onView(withId(R.id.button))
                .perform(click());

    }

}
