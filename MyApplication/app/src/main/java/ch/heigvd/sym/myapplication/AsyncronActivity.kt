package ch.heigvd.sym.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.heigvd.sym.myapplication.databinding.ActivityAsyncronBinding
import java.util.*

class AsyncronActivity : CommunicationEventListener, AppCompatActivity() {
    private lateinit var binding: ActivityAsyncronBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAsyncronBinding.inflate(layoutInflater)
        setContentView(binding.root);
        val symComManager = SymComManager(this)

        this.binding.sendButton.setOnClickListener {
            symComManager.sendRequest("http://mobile.iict.ch/api/txt", binding.userInput.text.toString())
        }
    }
    override fun handleServerResponse(response: String) {
        binding.textAnswer.text = response;
    }
}