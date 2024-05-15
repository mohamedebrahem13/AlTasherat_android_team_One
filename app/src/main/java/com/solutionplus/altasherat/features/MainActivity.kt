package com.solutionplus.altasherat.features

import android.os.Bundle
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun viewInit() {
        binding.textHelloWorld.text = "welcome"
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
        logger.debug("onActivityReady")
    }

    companion object {
        private val logger = getClassLogger()
    }
}