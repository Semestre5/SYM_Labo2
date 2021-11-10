package ch.heigvd.sym.myapplication

import android.os.Looper
import android.os.Handler
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import kotlin.concurrent.thread

class SymComManager(var communicationEventListener: CommunicationEventListener? = null) {
    fun sendRequest(url: String, request: String, content_type: String = "text/plain") {
            thread() {
                val connection = URL(url).openConnection() as HttpURLConnection
                val requestSerialized: ByteArray = request.toByteArray(StandardCharsets.UTF_8);
                connection.requestMethod = "POST";
                connection.setRequestProperty("Content-Type", content_type);
                // connection.setRequestProperty("Accept-Charset", "UTF-8");
                connection.doOutput = true;

                try {
                    connection.outputStream.write(requestSerialized);
                    connection.outputStream.close();
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }

                try {
                    val br = BufferedReader(
                        InputStreamReader(connection.inputStream)
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