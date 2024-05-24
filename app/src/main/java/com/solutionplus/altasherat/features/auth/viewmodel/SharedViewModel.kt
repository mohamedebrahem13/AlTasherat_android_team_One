package com.solutionplus.altasherat.features.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solutionplus.altasherat.features.auth.ui.listener.LoginSignupButtonListener

class SharedViewModel: ViewModel() {

    var listener: LoginSignupButtonListener? = null
    fun setUpListener(listener: LoginSignupButtonListener) {
        this.listener = listener
    }

    val buttonText = MutableLiveData<String>()
    fun setButtonText(text: String) {
        buttonText.value = text
    }



}