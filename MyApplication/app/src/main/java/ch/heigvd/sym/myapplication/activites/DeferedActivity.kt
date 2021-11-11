package ch.heigvd.sym.myapplication.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.heigvd.sym.myapplication.Utils
import ch.heigvd.sym.myapplication.communication.CommunicationEventListener
import ch.heigvd.sym.myapplication.communication.SymComManager
import ch.heigvd.sym.myapplication.databinding.ActivityDeferedBinding
import java.util.*

class DeferedActivity : CommunicationEventListener, AppCompatActivity() {
    private lateinit var binding: ActivityDeferedBinding
    private val queue: Queue<String> = LinkedList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeferedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val symComManager = SymComManager(this)
        binding.sendButton.setOnClickListener {
            queue.add(binding.userInput.text.toString())
        }

        object : Thread() {
            override fun run() {
                while (true) {
                    for(item : String in queue){
                        symComManager.sendRequest(Utils.URL_TXT, item.toByteArray())
                    }
                    sleep(10000)
                }
            }
        }.start()

    }
    override fun handleServerResponse(response: String) {
        queue.remove(response)
        val textOld : String = binding.textAnswer.text.toString()
        binding.textAnswer.text = textOld + System.getProperty ("line.separator") + response
    }
}