package ch.heigvd.sym.myapplication.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.heigvd.sym.myapplication.Utils
import ch.heigvd.sym.myapplication.communication.CommunicationEventListener
import ch.heigvd.sym.myapplication.communication.SymComManager
import ch.heigvd.sym.myapplication.databinding.ActivityDeferredBinding
import java.util.*

/**
 * Authors : Axel Vallon, Lev Pozniakoff and Robin Gaudin
 * Date : 12.11.2021
 * DeferredActivity : Activity tht allow a user to send messages to the server. The messages are
 * send again until the activity receive the same message from the server
 */

class DeferredActivity : CommunicationEventListener, AppCompatActivity() {
    private lateinit var binding: ActivityDeferredBinding
    private val queue: Queue<String> = LinkedList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeferredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val symComManager = SymComManager(this)
        binding.sendButton.setOnClickListener {
            //request added to pending request queue
            queue.add(binding.userInput.text.toString())
        }

        // this thread try to send every DEFERRED_TIMING all pending request, active for this
        // activity life
        object : Thread() {
            override fun run() {
                while (true) {
                    for(item : String in queue){
                        symComManager.sendRequest(Utils.URL_TXT, item.toByteArray())
                    }
                    sleep(Utils.DEFERRED_TIMING)
                }
            }
        }.start()

    }
    override fun handleServerResponse(response: String) {
        //if an element is send, it means we can suppress it and log it
        queue.remove(response)
        val textOld : String = binding.textAnswer.text.toString()
        binding.textAnswer.text = textOld + System.getProperty ("line.separator") + response
    }
}