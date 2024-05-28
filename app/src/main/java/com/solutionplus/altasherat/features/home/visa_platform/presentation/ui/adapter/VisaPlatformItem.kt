package com.solutionplus.altasherat.features.home.visa_platform.presentation.ui.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class VisaPlatformItem(
    val type: VisaTypeEnum,
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