package com.solutionplus.altasherat.features.splash.presention.ui.activity

import android.os.Bundle

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>() {
    private lateinit var navController: NavController

    override fun viewInit() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.onBoarding_fragment_nav_host) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
    }
    override fun onSupportNavigateUp(): Boolean {
        if (!navController.popBackStack()) {
            // Call finish() on your Activity
            finish()
        }
        return navController.navigateUp()||super.onSupportNavigateUp()
    }
}