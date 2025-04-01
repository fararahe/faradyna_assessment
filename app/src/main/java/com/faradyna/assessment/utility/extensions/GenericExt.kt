package com.faradyna.assessment.utility.extensions

import com.faradyna.assessment.utility.consts.MediaTypeUtil
import com.squareup.moshi.JsonAdapter
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun <T: Any> T.toRequestBody(adapter: JsonAdapter<T>): RequestBody {
    return adapter.toJson(this).toRequestBody(MediaTypeUtil.JSON)
}