package com.solutionplus.altasherat.common.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.solutionplus.altasherat.common.data.models.exception.bindView

abstract class BaseViewActivity<B : ViewBinding> : AppCompatActivity() {


    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindView()
        // Other common setup code can go here
    }
    abstract fun viewInit()
}