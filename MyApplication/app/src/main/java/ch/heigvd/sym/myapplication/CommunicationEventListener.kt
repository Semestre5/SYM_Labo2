package ch.heigvd.sym.myapplication

interface CommunicationEventListener {
    fun handleServerResponse(response :String)
}