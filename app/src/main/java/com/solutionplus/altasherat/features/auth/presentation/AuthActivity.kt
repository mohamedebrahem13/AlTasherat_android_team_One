package com.solutionplus.altasherat.features.auth.presentation

import android.content.res.Configuration
import android.os.Bundle
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {
    override fun viewInit() {}
    override fun onActivityReady(savedInstanceState: Bundle?) {}

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle theme change here, such as recreating the activity
        recreate()
    }

}