package aplicacion.android.danielvm.quicktestandroid.activities;


import android.os.SystemClock;
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

/**
 * Clase CourseActivityTest encargada de realizar las pruebas intrumentadas de UI
 * sobre la actividad CourseActivity.
 *
 * @author Daniel Puente Gabarri.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CourseActivityTest {

    private static final String mName = "profesor";
    private static final String mPass = "Asdf1234!";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void courseActivityTest() {
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

        SystemClock.sleep(2000);

        onView(withId(R.id.itemGridView))
                .perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.itemListView))
                .perform(click());

    }

}
