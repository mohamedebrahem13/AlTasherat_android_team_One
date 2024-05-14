package com.solutionplus.altasherat.common.presentation.ui.base.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.solutionplus.altasherat.android.extentions.bindView

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity() {

    private lateinit var _binding: Binding
    protected val binding: Binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = bindView()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewInit()
        onActivityReady(savedInstanceState)
    }

    abstract fun viewInit()
    abstract fun onActivityReady(savedInstanceState: Bundle?)
}