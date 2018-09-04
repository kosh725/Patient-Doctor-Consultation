package com.group4.patientdoctorconsultation;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.group4.patientdoctorconsultation.ui.NavigationActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HomeFragmentTest {

    @Rule
    public ActivityTestRule<NavigationActivity> navigationActivityTestRule = new ActivityTestRule(NavigationActivity.class);

    @Test
    public void clickOnNewPacket_OpenNewPacketDialog() {
        onView(withId(R.id.new_packet_card))
                .perform(click());
        onView(withId(R.id.text_input))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnPacket_OpenPacketFragment(){
        onView(withId(R.id.data_packet_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.doctor_icon))
                .check(matches(isDisplayed()));
    }
}
