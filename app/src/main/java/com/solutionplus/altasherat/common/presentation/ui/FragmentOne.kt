package com.solutionplus.altasherat.common.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOneBinding
// it is an example of using base fragment
class FragmentOne : BaseFragment<FragmentOneBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun showLoading(isLoading: Boolean) {
        TODO("Not yet implemented")
    }

    override fun collectViewEvents() {
        TODO("Not yet implemented")
    }

    override fun setupUI() {
        TODO("Not yet implemented")
    }

    override fun observeViewState() {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        try {

        }catch (ex:AlTasheratException){
            // if you want to handel exception you can call it like this and pass the exception
            handleException(ex)

        }

        return inflater.inflate(R.layout.fragment_one, container, false)
    }

}