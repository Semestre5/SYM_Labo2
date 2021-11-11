package ch.heigvd.sym.myapplication.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ch.heigvd.sym.myapplication.CommunicationEventListener
import ch.heigvd.sym.myapplication.SymComManager
import ch.heigvd.sym.myapplication.databinding.ActivityCompressBinding

class CompressActivity : CommunicationEventListener, AppCompatActivity() {
    private lateinit var binding: ActivityCompressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompressBinding.inflate(layoutInflater)
        setContentView(binding.root);
        val symComManager = SymComManager(this)

        binding.sendButton.setOnClickListener {
            Log.d("input", binding.userInput.text.toString())
            symComManager.sendRequest("http://mobile.iict.ch/api/txt", binding.userInput.text.toString().toByteArray(), compressed = true)
        }
    }

    override fun handleServerResponse(response: String) {
        binding.textAnswer.text = response
    }
}