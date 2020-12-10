package org.AimEx.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Rule
import org.junit.Test
import org.ligi.trulesk.TruleskActivityRule
import org.AimEx.R
import org.AimEx.accounts.AccountPickActivity

class TheAccountPickActivity {

    @get:Rule
    var rule = TruleskActivityRule(AccountPickActivity::class.java)

    @Test
    fun listShows() {

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        rule.screenShot("reference_list")
    }
}
