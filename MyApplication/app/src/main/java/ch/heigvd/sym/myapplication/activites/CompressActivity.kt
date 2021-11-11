package ch.heigvd.sym.myapplication.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.heigvd.sym.myapplication.Utils
import ch.heigvd.sym.myapplication.communication.CommunicationEventListener
import ch.heigvd.sym.myapplication.communication.SymComManager
import ch.heigvd.sym.myapplication.databinding.ActivityCompressBinding

class CompressActivity : CommunicationEventListener, AppCompatActivity() {
    private lateinit var binding: ActivityCompressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val symComManager = SymComManager(this)

        binding.sendButton.setOnClickListener {
            symComManager.sendRequest(Utils.URL_TXT, binding.userInput.text.toString().toByteArray(), compressed = true)
        }
    }

    override fun handleServerResponse(response: String) {
        binding.textAnswer.text = response
    }
}