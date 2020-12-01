package com.example.android.bookreader.api

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.assertThat

@RunWith(JUnit4::class)
class ApiServiceTest {

    private val service = ApiService()

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun startWebServer() {
        mockWebServer = MockWebServer()
    }

    @After
    fun stopWebServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetBook() {
        addResponse("test-book.txt")
        val response = service.getBook(mockWebServer.url("/test-book.txt").toString())
        val request = mockWebServer.takeRequest()
        val content = response.bufferedReader().readLine()

        assertThat(request.path, `is`("/test-book.txt"))
        assertThat(response, notNullValue())
        assertThat(content, `is`("aa bb aa a'a..aa"))
    }

    private fun addResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val mockResponse = MockResponse()
        mockResponse.setBody(inputStream.bufferedReader().readLine())

        mockWebServer.enqueue(mockResponse)
    }
}