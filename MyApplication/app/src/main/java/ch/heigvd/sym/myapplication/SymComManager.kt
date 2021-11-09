package ch.heigvd.sym.myapplication

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.UTFDataFormatException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import kotlin.concurrent.thread

class SymComManager(var communicationEventListener: CommunicationEventListener? = null) {
    fun sendRequest(url: String, request: String) {
            thread() {
                val connection = URL(url).openConnection() as HttpURLConnection
                val requestSerialized: ByteArray = request.toByteArray(StandardCharsets.UTF_8);
                connection.requestMethod = "POST";
                connection.setRequestProperty("Content-Type", "text/plain");
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
                    communicationEventListener?.handleServerResponse(response)
                    br.close()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                } finally {
                    connection.disconnect()
                }
            }
    }
}