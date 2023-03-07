package com.lakhal.cryptoprice.ui.cryptoList

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation.setViewNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.lakhal.cryptoprice.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class CryptoListFragmentTest {

    @Test
    fun testNavigationToCryptoDetailsScreen() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        // Create a graphical FragmentScenario for the TitleScreen
        val fragmentScenario = launchFragmentInContainer {
            CryptoListFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        // The fragment’s view has just been created
                        navController.setGraph(R.navigation.mobile_navigation)
                        setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }
        navController.navigate(R.id.action_navigation_home_to_navigation_crypto_details)

        onView(ViewMatchers.withId(R.id.crypto_list_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CryptoMoneyAdapter.CryptoMoneyViewHolder>(3, click()))

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.navigation_crypto_details)
    }
}