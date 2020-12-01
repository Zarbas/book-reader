package com.example.android.bookreader.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.android.bookreader.R
import com.example.android.bookreader.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun openReadBookAndBack() {
        Espresso.onView(withId(R.id.openReadBook)).perform(click())
        Espresso.onView(withId(R.id.local)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.remote)).check(matches(isDisplayed()))
        Espresso.pressBack()
        Espresso.onView(withId(R.id.wordsList)).check(matches(isDisplayed()))
    }

    fun openReadFromUrlAndBack() {
        Espresso.onView(withId(R.id.openReadBook)).perform(click())
        Espresso.onView(withId(R.id.remote)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.remote)).perform(click())
        Espresso.onView(withId(R.id.bookURL)).perform(click()).perform(typeText("http://test"))
        Espresso.onView(withId(R.id.bookURL)).check(matches(withText("http://test")))
        Espresso.onView(withText("Cancel")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click())
        Espresso.pressBack()
    }
}