package ch.heigvd.sym.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.heigvd.sym.myapplication.databinding.ActivitySerializationBinding

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
                    symComManager.sendRequest("http://mobile.iict.ch/api/xml",
                        directory.serializeXML().toByteArray(),
                        "application/xml")
                binding.radioProtobuf.id ->
                    symComManager.sendRequest(
                        "http://mobile.iict.ch/api/protobuf",
                        directory.serializeProtoBuf(),
                        "application/protobuf")
                binding.radioJson.id ->
                    symComManager.sendRequest("http://mobile.iict.ch/api/json",
                        person.serializeJSON().toByteArray(),
                        "application/json")
            }
            //binding.textViewAnswer.text = Parser().deserializeXML(serialized).persons.toString()
        }

    }
    override fun handleServerResponse(response: String) {
        when(binding.radioSer.checkedRadioButtonId) {
            binding.radioXml.id ->
                binding.textViewAnswer.text = Parser().deserializeXML(response).toString()
            binding.radioProtobuf.id ->
                binding.textViewAnswer.text = Parser().deserializeProtoBuf(response).toString()
            binding.radioJson.id ->
                binding.textViewAnswer.text = Parser().deserializeJSON(response).toString()
        }
    }
}