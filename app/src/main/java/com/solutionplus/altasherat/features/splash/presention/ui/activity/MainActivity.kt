package com.solutionplus.altasherat.features.splash.presention.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.extentions.hideStatusBars
import com.solutionplus.altasherat.android.extentions.showStatusBars
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var navController: NavController

    override fun viewInit() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splash -> hideStatusBars()
                else -> showStatusBars()
            }
        }
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
        logger.debug("onActivityReady")
    }

    companion object {
        private val logger = getClassLogger()
    }
}