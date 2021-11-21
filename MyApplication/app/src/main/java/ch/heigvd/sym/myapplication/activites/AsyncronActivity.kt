package ch.heigvd.sym.myapplication.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ch.heigvd.sym.myapplication.Utils
import ch.heigvd.sym.myapplication.communication.CommunicationEventListener
import ch.heigvd.sym.myapplication.communication.SymComManager
import ch.heigvd.sym.myapplication.databinding.ActivityAsyncronBinding
import java.util.*

/**
 * Authors : Axel Vallon, Lev Pozniakoff and Robin Gaudin
 * Date : 12.11.2021
 * AsynconActivity : Send a message to a server and show it
 */

class AsyncronActivity : CommunicationEventListener, AppCompatActivity() {
    private lateinit var binding: ActivityAsyncronBinding
    private lateinit var instant1: Date
    private lateinit var instant2: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAsyncronBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val symComManager = SymComManager(this)

        /* send a new message */
        this.binding.sendButton.setOnClickListener {
            instant1 = Date();
            symComManager.sendRequest(Utils.URL_TXT, binding.userInput.text.toString().toByteArray())
        }
    }

    /* server answer */
    override fun handleServerResponse(response: String) {
        instant2 = Date()
        val diff: Long = instant2.time - instant1.time
        val mili = diff
        displayToast(this , mili);
        binding.textAnswer.text = response
    }

    /**
     * Affiche un toast du temps d'execution
     * @param activity
     * @param toastText
     */
    fun displayToast(activity: AppCompatActivity?, toastText: Long) {
        val toast = Toast.makeText(activity, toastText.toString(), Toast.LENGTH_SHORT)
        toast.show()
    }

}