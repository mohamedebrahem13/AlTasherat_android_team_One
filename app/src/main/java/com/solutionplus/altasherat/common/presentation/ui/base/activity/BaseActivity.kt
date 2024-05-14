package com.solutionplus.altasherat.common.presentation.ui.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.solutionplus.altasherat.android.extentions.bindView

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity() {

    private lateinit var _binding: Binding
    protected val binding: Binding
        get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindView()
        viewInit()
        onActivityReady(savedInstanceState)
    }
    abstract fun viewInit()
    abstract fun onActivityReady (savedInstanceState: Bundle?)
}