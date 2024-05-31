package com.solutionplus.altasherat.features.auth.presentation

import android.os.Bundle
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {
    override fun viewInit() {}
    override fun onActivityReady(savedInstanceState: Bundle?) {}
}