package testutil

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import java.util.logging.Level
import java.util.logging.Logger

fun MockWebServer.startSilently() =
    this.run {
        Logger.getLogger(this::class.java.name).level = Level.WARNING
        start()
    }

fun MockWebServer.mockSuccessResponse(path: String? = null) =
    (if (path == null) "{}" else ResourceReader.read(path)).let {
        this.enqueue(MockResponse().setResponseCode(200).setBody(it))
    }

fun MockWebServer.mockErrorResponse(code: Int, path: String? = null) =
    (if (path == null) "{}" else ResourceReader.read(path)).let {
        this.enqueue(MockResponse().setResponseCode(code).setBody(it))
    }

fun MockWebServer.mockTimeoutResponse() =
    this.enqueue(MockResponse().apply { socketPolicy = SocketPolicy.NO_RESPONSE })

object ResourceReader {
    fun read(path: String): String = this.javaClass.classLoader!!.getResource(path).readText()
}