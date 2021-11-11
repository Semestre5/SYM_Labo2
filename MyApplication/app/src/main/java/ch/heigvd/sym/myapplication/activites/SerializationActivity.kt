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

class SerializationActivity : CommunicationEventListener, AppCompatActivity() {
    private lateinit var binding: ActivitySerializationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerializationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val symComManager = SymComManager(this)

        binding.buttonSend.setOnClickListener {
            var typePhone = ""
            when(binding.radioPhone.checkedRadioButtonId) {
                binding.radioHome.id -> typePhone = "home"
                binding.radioWork.id -> typePhone = "work"
                binding.radioMobile.id -> typePhone =  "mobile"
            }
            val phone = Phone(binding.inputPhone.text.toString(), typePhone)
            val person = Person(
                binding.inputName.text.toString(),
                binding.inputFirstname.text.toString(),
                listOf(phone) as MutableList<Phone>,
                binding.inputMiddleName.text.toString()
            )
            val directory = Directory()
            directory.addPerson(person)
            when(binding.radioSer.checkedRadioButtonId) {
                binding.radioXml.id ->
                    symComManager.sendRequest(
                        Utils.URL_XML, directory.serializeXML().toByteArray(), Utils.CONTENT_XML)
                binding.radioProtobuf.id ->
                    symComManager.sendRequest(
                        Utils.URL_PROTOBUF, directory.serializeProtoBuf(), Utils.CONTENT_PROTOBUF)
                binding.radioJson.id ->
                    symComManager.sendRequest(
                        Utils.URL_JSON, person.serializeJSON().toByteArray(), Utils.CONTENT_JSON)
            }
        }

    }
    override fun handleServerResponse(response: String) {
        when(binding.radioSer.checkedRadioButtonId) {
            binding.radioXml.id ->
                binding.textViewAnswer.text = Directory.deserializeXML(response).toString()
            binding.radioProtobuf.id ->
                binding.textViewAnswer.text = Directory.deserializeProtoBuf(response).toString()
            binding.radioJson.id ->
                binding.textViewAnswer.text = Person.deserializeJSON(response).toString()
        }
    }
}