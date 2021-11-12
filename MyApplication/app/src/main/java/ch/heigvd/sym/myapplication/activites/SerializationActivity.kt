package ch.heigvd.sym.myapplication.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.heigvd.sym.myapplication.Utils
import ch.heigvd.sym.myapplication.communication.CommunicationEventListener
import ch.heigvd.sym.myapplication.communication.SymComManager
import ch.heigvd.sym.myapplication.databinding.ActivitySerializationBinding
import ch.heigvd.sym.myapplication.model.Directory
import ch.heigvd.sym.myapplication.model.Person
import ch.heigvd.sym.myapplication.model.Phone

/**
 * Authors : Axel Vallon, Lev Pozniakoff and Robin Gaudin
 * Date : 12.11.2021
 * SerializationActivity: Serialize a contact form in JSON, XML or Protocol Buffer, send it to
 * a server, and Parse the answer to print the contact
 */

class SerializationActivity : CommunicationEventListener, AppCompatActivity() {
    private lateinit var binding: ActivitySerializationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerializationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val symComManager = SymComManager(this)

        binding.buttonSend.setOnClickListener {
            var typePhone = ""
            // get user UI selection for the phone type
            when(binding.radioPhone.checkedRadioButtonId) {
                binding.radioHome.id -> typePhone = Utils.TAG_TYPE_HOME
                binding.radioWork.id -> typePhone = Utils.TAG_TYPE_WORK
                binding.radioMobile.id -> typePhone =  Utils.TAG_TYPE_MOBILE
            }
            // Phone creation
            val phone = Phone(binding.inputPhone.text.toString(), typePhone)
            // Person creation, it's possible to add more Phone here
            val person = Person(
                binding.inputName.text.toString(),
                binding.inputFirstname.text.toString(),
                listOf(phone) as MutableList<Phone>,
                binding.inputMiddleName.text.toString()
            )
            // Directory creation, it's possible to add more persons here
            val directory = Directory()
            directory.addPerson(person)
            // send Directory in selected format
            when(binding.radioSer.checkedRadioButtonId) {
                binding.radioXml.id ->
                    symComManager.sendRequest(
                        Utils.URL_XML, directory.serializeXML().toByteArray(), Utils.CONTENT_XML)
                binding.radioProtobuf.id ->
                    symComManager.sendRequest(
                        Utils.URL_PROTOBUF, directory.serializeProtoBuf(), Utils.CONTENT_PROTOBUF)
                binding.radioJson.id ->
                    symComManager.sendRequest(
                        Utils.URL_JSON, directory.serializeJSON().toByteArray(), Utils.CONTENT_JSON)
            }
        }

    }
    override fun handleServerResponse(response: String) {
        // parse the response answer with current user selection. Amelioration possible : Create a
        // way to have an handleServerResponse associeted with send format
        when(binding.radioSer.checkedRadioButtonId) {
            binding.radioXml.id ->
                binding.textViewAnswer.text = Directory.deserializeXML(response).toString()
            binding.radioProtobuf.id ->
                binding.textViewAnswer.text = Directory.deserializeProtoBuf(response).toString()
            binding.radioJson.id ->
                binding.textViewAnswer.text = Directory.deserializeJSON(response).toString()
        }
    }
}