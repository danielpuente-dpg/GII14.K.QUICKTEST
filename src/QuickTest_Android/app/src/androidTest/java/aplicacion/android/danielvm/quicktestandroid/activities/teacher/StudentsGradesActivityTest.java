package aplicacion.android.danielvm.quicktestandroid.activities.teacher;

import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import aplicacion.android.danielvm.quicktestandroid.R;
import aplicacion.android.danielvm.quicktestandroid.activities.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Clase StudentsGradesActivityTest encargada de realizar las pruebas intrumentadas de UI
 * sobre la actividad StudentsGradesActivity.
 *
 * @author Daniel Puente Gabarri.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StudentsGradesActivityTest {

    private static final String mName = "profesor";
    private static final String mPass = "Asdf1234!";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void studentGradesActivityTest() {
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

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        withId(R.id.listViewCourse),
                        2),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.itemGridView), withContentDescription("Grid View"), isDisplayed()));
        actionMenuItemView.perform(click());

        SystemClock.sleep(2000);

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.itemListView), withContentDescription("List View"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        withId(R.id.listViewQuestionary),
                        0),
                        isDisplayed()));
        linearLayout2.perform(click());

        SystemClock.sleep(2000);

        ViewInteraction linearLayout3 = onView(
                allOf(childAtPosition(
                        withId(R.id.listViewStudentGrade),
                        0),
                        isDisplayed()));
        linearLayout3.perform(click());

        SystemClock.sleep(2000);

        ViewInteraction linearLayout4 = onView(
                allOf(childAtPosition(
                        withId(R.id.listViewStudentGrade),
                        1),
                        isDisplayed()));
        linearLayout4.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}