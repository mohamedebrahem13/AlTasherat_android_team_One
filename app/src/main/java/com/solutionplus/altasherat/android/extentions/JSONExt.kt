package com.solutionplus.altasherat.android.extentions

import com.google.gson.Gson
import com.solutionplus.altasherat.common.domain.repository.local.encryption.ISecretKeyAliasEnum
import java.lang.reflect.Type

fun <M> String.getModelFromJSON(tokenType: Type): M = Gson().fromJson(this, tokenType)
fun <M> M.toJson(): String = Gson().toJson(this)

