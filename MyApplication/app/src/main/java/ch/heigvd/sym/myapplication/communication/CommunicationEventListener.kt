package ch.heigvd.sym.myapplication.communication

interface CommunicationEventListener {
    fun handleServerResponse(response :String)
}