package com.antho.newsreader.view.activities;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import androidx.test.InstrumentationRegistry;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import com.antho.newsreader.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class  MainActivityTest
{
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
    private MainActivity mainActivity = null;
    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);
    @Before
    public void setUp() throws Exception
    {
        mainActivity = mainActivityRule.getActivity();
    }
    // Context of the app under test.
    @Test
    public void useAppContext() throws Exception
    {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.antho.newsreader", appContext.getPackageName());
    }

    @Test
    public void testLaunchSearchActivity()
    {
        onView(withId(R.id.search_item)).perform(click());
        mainActivityRule.launchActivity(new Intent());
    }
    @Test
    public void testLaunchNotificationsActivity()
    {
        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
        getInstrumentation().invokeMenuActionSync(mainActivity, R.id.settings_notifications, 0);
        mainActivityRule.launchActivity(new Intent());
    }
    @Test
    public void testChangeFragment()
    {
        onView(withId(R.id.action_world)).perform(click());
        onView(withId(R.id.action_business)).perform(click());
        onView(withId(R.id.action_popular)).perform(click());
        onView(withId(R.id.action_sports)).perform(click());
    }
}