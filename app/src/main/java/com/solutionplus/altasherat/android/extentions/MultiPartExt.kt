package com.solutionplus.altasherat.android.extentions

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun List<File>.createPartFromFilesList(key: String): ArrayList<MultipartBody.Part> {
    val parts: ArrayList<MultipartBody.Part> = ArrayList()
    forEachIndexed { index, file ->
        parts.add(get(index).createPartFromFile(key.plus("[".plus(file).plus("]"))))
    }
    return parts
}

fun String.createPartFromString(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}

fun File.createPartFromFile(key: String): MultipartBody.Part {
    val body: RequestBody = asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(key, name, body)
}