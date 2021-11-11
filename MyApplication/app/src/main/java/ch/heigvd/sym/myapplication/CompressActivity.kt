package ch.heigvd.sym.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            symComManager.sendRequest("http://mobile.iict.ch/api/txt", binding.userInput.text.toString(), compressed = true)
        }
    }

    override fun handleServerResponse(response: String) {
        binding.textAnswer.text = response
    }
}