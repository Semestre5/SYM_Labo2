package ch.heigvd.sym.myapplication

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.UTFDataFormatException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class SymComManager(var communicationEventListener: CommunicationEventListener? = null) {
    fun sendRequest(url: String, request: String) {
        val connection = URL(url).openConnection() as HttpURLConnection

        val requestSerialized : ByteArray = request.toByteArray(StandardCharsets.UTF_8);
        connection.requestMethod = "POST";
        connection.setRequestProperty("Content-Type", "text/plain");
        try {
            connection.outputStream.write(requestSerialized);
            connection.outputStream.close();
        } catch (exception: Exception) {
            exception.printStackTrace()
            return
        } /*finally {
            connection.disconnect()
        }*/

        try {
            val br = BufferedReader(
                InputStreamReader(connection.inputStream)
            )
            val response : String = br.readText()
            br.close()
        }
        catch (exception: Exception) {
            exception.printStackTrace()
            return
        }/* finally {
            connection.disconnect()
        }*/
        }
}