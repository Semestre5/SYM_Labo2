package ch.heigvd.sym.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.heigvd.sym.myapplication.databinding.ActivitySerializationBinding

class SerializationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySerializationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerializationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSend.setOnClickListener {
            var typephone = "";
            when(binding.radioPhone.checkedRadioButtonId) {
                binding.radioHome.id -> typephone = "home"
                binding.radioWork.id -> typephone = "work"
                binding.radioMobile.id -> typephone =  "mobile"
            }
            var person = Person(
                binding.inputName.text.toString(),
                binding.inputFirstname.text.toString(),
                binding.inputMail.text.toString(),
                binding.inputPhone.text.toString(),
                typephone,
                binding.inputMiddleName.text.toString()

            )
            person.typephone = typephone
            var serialized = "";
            when(binding.radioSer.checkedRadioButtonId) {
                binding.radioXml.id -> serialized = person.serializeXML()
                binding.radioProtobuf.id -> serialized = person.serializeProtoBuf()
                binding.radioJson.id -> serialized = person.serializeJSON()
            }
            binding.textViewAnswer.text = serialized
            //binding.textViewAnswer.text = Parser().deserializeXML(serialized).persons.toString()
        }
    }

}