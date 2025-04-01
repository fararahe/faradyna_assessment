package com.faradyna.assessment.core.configs.networking

import com.faradyna.assessment.utility.consts.MediaTypeUtil
import com.faradyna.assessment.utility.consts.ResponseCode
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object ApiHandler {
    suspend fun <T : Any> handleApi(
        errorHandler: ErrorResponseHandler = DefaultErrorResponseHandler(),
        block: suspend () -> Response<T>
    ): T? {
        try {
            val response = block.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                return body
            } else {
                val errorHtml = response.errorBody()?.string() ?: "Unknown error"

                // Optionally, extract plain text if possible
                val extractedMessage = extractPlainTextFromHtml(errorHtml)

                // Convert extracted message to JSON format
                val jsonErrorMessage = """{"message": "${escapeJson(extractedMessage)}"}"""

                // Throwing an error with the extracted message
                throw errorHandler.handle(
                    jsonErrorMessage.toResponseBody(MediaTypeUtil.JSON),
                    if (extractedMessage.isNotEmpty()) ResponseCode.BAD_REQUEST else response.code()
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private fun extractPlainTextFromHtml(html: String): String {
        val regex = "<body.*?>(.*?)</body>".toRegex(RegexOption.DOT_MATCHES_ALL)
        return regex.find(html)?.groupValues?.get(1)?.replace(Regex("<.*?>"), "")?.trim()
            ?: html.trim()
    }

    fun escapeJson(text: String): String {
        return text.replace("\"", "\\\"")
    }

}