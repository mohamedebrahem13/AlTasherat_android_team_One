package com.solutionplus.altasherat.android.extentions

import com.google.gson.Gson
import java.lang.reflect.Type

fun <M> String.getModelFromJSON(tokenType: Type): M = Gson().fromJson(this, tokenType)