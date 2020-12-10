package org.AimEx.tests

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import org.AimEx.R
import org.AimEx.accounts.KeyType
import org.AimEx.accounts.getCreateImportIntentFor
import org.AimEx.infrastructure.TestApp

class TheImportWithPayload {

    @Test
    fun startOfECDSAWorks() {
        val context = ApplicationProvider.getApplicationContext<TestApp>()
        context.startActivity(context.getCreateImportIntentFor("keyProbe", KeyType.ECDSA).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })

        onView(withId(R.id.type_ecdsa_select)).check(matches(isChecked()))
        onView(withId(R.id.type_json_select)).check(matches(isNotChecked()))
        onView(withId(R.id.key_content)).check(matches(withText("keyProbe")))
    }

    @Test
    fun startOfJSONWorks() {
        val context = ApplicationProvider.getApplicationContext<TestApp>()
        context.startActivity(context.getCreateImportIntentFor("{}", KeyType.JSON).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })

        onView(withId(R.id.type_json_select)).check(matches(isChecked()))
        onView(withId(R.id.type_ecdsa_select)).check(matches(isNotChecked()))
        onView(withId(R.id.key_content)).check(matches(withText("{}")))
    }
}
