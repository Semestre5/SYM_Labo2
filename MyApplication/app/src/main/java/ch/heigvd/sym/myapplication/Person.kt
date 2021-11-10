package ch.heigvd.sym.myapplication

import com.google.gson.Gson
import android.util.Xml
import java.io.StringWriter


data class Person(
    var name: String,
    var firstname: String,
    var phones: MutableList<Phone> = emptyList<Phone>().toMutableList(),
    var middle_name: String? = null
) {

    fun serializeProtoBuf(): DirectoryOuterClass.Person? {
        val person = DirectoryOuterClass.Person.newBuilder()
            .setName(this.name)
            .setFirstname(this.firstname)

        if (this.middle_name != null && this.middle_name != ""){
            person.middlename = this.middle_name
        }
        for (phone in phones){
            person.addPhone(phone.serializeProtoBuf())
        }
        return person.build()
    }

    fun serializeJSON(): String {
        return Gson().toJson(this)
    }
}