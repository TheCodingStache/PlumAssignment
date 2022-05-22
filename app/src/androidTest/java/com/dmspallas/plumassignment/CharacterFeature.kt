package com.dmspallas.plumassignment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.ActivityTestRule
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.dmspallas.plumassignment.di.idlingResource
import com.dmspallas.plumassignment.presentation.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit.rule

class CharacterFeature : BaseUITest() {
    @get:Rule
    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun displayScreenTitle() {
        assertDisplayed("Superhero Squad Maker")
    }

    @Test
    fun displayListOfCharacters() {
        assertRecyclerViewItemCount(R.id.recycler_view, 20)
        onView(
            CoreMatchers.allOf(
                withId(R.id.name),
                isDescendantOfA(nthChildOf(withId(R.id.recycler_view), 0))
            )
        ).check(matches(withText("3-D Man"))).check(matches(isDisplayed()))
    }

    @Test
    fun displayLoaderWhileFetchingSports() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hidesLoader() {
        assertNotDisplayed(R.id.loader)
    }
}