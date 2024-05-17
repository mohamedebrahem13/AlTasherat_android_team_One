package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>() {
    private lateinit var navController: NavController

    override fun viewInit() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.onboardingNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
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