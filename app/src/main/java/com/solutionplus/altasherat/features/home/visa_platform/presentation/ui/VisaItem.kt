package com.solutionplus.altasherat.features.home.visa_platform.presentation.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class VisaItem(
    @StringRes
    val titleFirstPart: Int,
    @StringRes
    val titleSecondPart: Int,
    @StringRes
    val buttonText: Int,
    @StringRes
    val actionText: Int,
    @DrawableRes
    val image: Int
)