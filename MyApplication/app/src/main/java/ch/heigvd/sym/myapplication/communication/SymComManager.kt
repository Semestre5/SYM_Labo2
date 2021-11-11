package ch.heigvd.sym.myapplication.communication

import android.os.Looper
import android.os.Handler
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream
import java.util.zip.Inflater
import java.util.zip.InflaterInputStream
import kotlin.concurrent.thread

class SymComManager(var communicationEventListener: CommunicationEventListener? = null) {
    fun sendRequest(url: String, request: ByteArray, content_type: String = "text/plain", compressed: Boolean = false) {
            thread {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", content_type)
                // Add theses headers if it must be compressed
                if (compressed) {
                    connection.setRequestProperty("X-Network", "CSD")
                    connection.setRequestProperty("X-Content-Encoding", "deflate")
                }
                connection.doOutput = true

                try {
                    val os: OutputStream
                    // Configure in case it must be compressed or not
                    if (compressed) {
                        os = DeflaterOutputStream(connection.outputStream, Deflater(9, true))
                    } else {
                        os = connection.outputStream
                    }
                    os.write(request)
                    os.close()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }

                try {
                    val br = BufferedReader(
                        // Check if the datas have been compressed or not
                        if (connection.headerFields["X-Content-Encoding"]?.isEmpty() == false) {
                            InputStreamReader(InflaterInputStream(connection.inputStream, Inflater(true)))
                        } else {
                            InputStreamReader(connection.inputStream)
                        }
                    )
                    val response: String = br.readText()

                    // Send the response in the main thread
                    val mHandler = Handler(Looper.getMainLooper())
                    val runnable = Runnable {communicationEventListener?.handleServerResponse(response)}
                    mHandler.post( runnable )

                    br.close()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                } finally {
                    connection.disconnect()
                }
            }
    }
}