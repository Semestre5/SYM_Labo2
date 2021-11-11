package ch.heigvd.sym.myapplication.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.heigvd.sym.myapplication.Utils
import ch.heigvd.sym.myapplication.communication.CommunicationEventListener
import ch.heigvd.sym.myapplication.communication.SymComManager
import ch.heigvd.sym.myapplication.databinding.ActivityAsyncronBinding

class AsyncronActivity : CommunicationEventListener, AppCompatActivity() {
    private lateinit var binding: ActivityAsyncronBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAsyncronBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val symComManager = SymComManager(this)

        /* send a new message */
        this.binding.sendButton.setOnClickListener {
            symComManager.sendRequest(Utils.URL_TXT, binding.userInput.text.toString().toByteArray())
        }
    }

    /* server answer */
    override fun handleServerResponse(response: String) {
        binding.textAnswer.text = response
    }
}