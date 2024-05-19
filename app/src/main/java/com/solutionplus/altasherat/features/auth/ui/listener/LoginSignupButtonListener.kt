package com.solutionplus.altasherat.features.auth.ui.listener

import android.graphics.drawable.Drawable

interface LoginSignupButtonListener {
    fun updateButtonIcon(icon: Drawable?)
    fun updateButtonText(text: String)
    fun triggerButton(trigger:() -> Unit)
}