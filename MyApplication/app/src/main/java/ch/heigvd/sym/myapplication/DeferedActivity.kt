package ch.heigvd.sym.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.heigvd.sym.myapplication.databinding.ActivityDeferedBinding
import java.util.*

class DeferedActivity : CommunicationEventListener, AppCompatActivity() {
    private lateinit var binding: ActivityDeferedBinding
    val queue: Queue<String> = LinkedList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeferedBinding.inflate(layoutInflater)
        setContentView(binding.root);

        val symComManager = SymComManager(this)
        binding.sendButton.setOnClickListener {
            queue.add(binding.userInput.text.toString());
        }

        object : Thread() {
            override fun run() {
                while (!queue.isEmpty()) {
                    for(item : String in queue){
                        symComManager.sendRequest("http://mobile.iict.ch/api/txt", item)
                    }
                    Thread.sleep(5000)
                }
            }
        }.start()


    }
    override fun handleServerResponse(response: String) {
        queue.remove(response);
        val text_old : String = binding.textAnswer.text.toString()
        binding.textAnswer.text = text_old + response;
    }
}