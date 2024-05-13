package com.solutionplus.altasherat.features

import android.os.Bundle
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseViewActivity
import com.solutionplus.altasherat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseViewActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewInit()
    }

    override fun viewInit() {
        binding.text2.text="welcome"
    }

    companion object {
        private val logger = getClassLogger()
    }
}