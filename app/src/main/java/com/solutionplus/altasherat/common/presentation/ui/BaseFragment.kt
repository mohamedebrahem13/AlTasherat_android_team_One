package com.solutionplus.altasherat.common.presentation.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.solutionplus.altasherat.common.data.models.exception.bindView

abstract class BaseFragment <B : ViewBinding>: Fragment()  {
    protected lateinit var binding: B


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindView()
        // Other common setup code can go here
    }
    abstract fun showLoading(isLoading: Boolean)

    // Abstract private method to collect view events (enforced implementation)
    abstract fun collectViewEvents()

    // for ui component like set click listener
    abstract fun setupUI()

    // Abstract method to observe view states (enforced implementation)
    abstract fun observeViewState()

}