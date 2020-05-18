package com.mygate.airtelx

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.mygate.airtelx.ui.FeedActivity
import com.mygate.airtelx.ui.adapter.AddressListAdapter

import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FeedActivityTest {

    @Rule
    @JvmField
    public var rule: ActivityTestRule<FeedActivity> =
        ActivityTestRule<FeedActivity>(FeedActivity::class.java)


    @Before
    fun setUp() {
    }


    @Test
    fun testSampleRecyclerVisible() {
        Thread.sleep(2000)
        onView(withId(R.id.searchInputView)).perform(typeText("Airtel"))
        Thread.sleep(2000)

    }


    @Test
    fun testCaseForRecyclerScroll() { // Get total item of RecyclerView
        Thread.sleep(2000)
        onView(withId(R.id.searchInputView)).perform(typeText("hello"))
        Thread.sleep(2000)
        val recyclerView: RecyclerView =
            rule.getActivity().findViewById(R.id.rView)
        val itemCount = recyclerView.adapter!!.itemCount
        // Scroll to end of page with position
       onView(withId(R.id.rView))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(rule.getActivity().getWindow().getDecorView())
                )
            )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))

        Thread.sleep(2000)

    }



    @After
    fun tearDown() {
    }
}