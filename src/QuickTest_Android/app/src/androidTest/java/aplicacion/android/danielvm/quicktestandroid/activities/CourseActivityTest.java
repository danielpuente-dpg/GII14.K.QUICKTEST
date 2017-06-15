package aplicacion.android.danielvm.quicktestandroid.activities;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import aplicacion.android.danielvm.quicktestandroid.R;

import static org.junit.Assert.*;

/**
 * Created by Daniel on 15/06/2017.
 */
@RunWith(AndroidJUnit4.class)
public class CourseActivityTest {

    @Rule
    public ActivityTestRule<CourseActivity> mCourseActivity = new ActivityTestRule<>(CourseActivity.class);
    @Before
    public void initValid(){
        LoginActivityTest loginActivityTest = new LoginActivityTest();
        loginActivityTest.loginActivityTest();
    }
    @Test
    public void onItemClick(){
        /*Espresso.onView(withId(R.id.listViewCourse))
                .perform(click());*/
    }


}