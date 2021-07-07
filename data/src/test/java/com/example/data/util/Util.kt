package com.example.data.util

import okhttp3.mockwebserver.MockResponse

object Util {
    private fun loadJson(path: String): String {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream(path)
        return inputStream?.bufferedReader().use { it?.readText() } ?: ""
    }

    fun mockResponse(fileName: String, responseCode: Int): MockResponse {
        return MockResponse().setResponseCode(responseCode)
            .setChunkedBody(loadJson(fileName), 32)
    }
}