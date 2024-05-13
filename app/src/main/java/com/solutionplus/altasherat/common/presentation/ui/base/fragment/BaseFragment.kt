package com.solutionplus.altasherat.common.presentation.ui.base.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.data.models.exception.bindView
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.delegate.DefaultResourceErrorHandlerDelegate
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.delegate.ExceptionHandler
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.delegate.InternetConnectionDelegate
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.delegate.InternetConnectionDelegateImpl

abstract class BaseFragment <B : ViewBinding>: Fragment(),
    ExceptionHandler by DefaultResourceErrorHandlerDelegate(),
    InternetConnectionDelegate by InternetConnectionDelegateImpl()
{
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

    // Call this function to explicitly handle exceptions
    fun handleException(exception: AlTasheratException) {
        handleException(exception, requireContext())
    }
    // Check for internet connectivity
    fun isInternetAvailable(): Boolean {
        return isInternetAvailable(requireContext())
    }

}