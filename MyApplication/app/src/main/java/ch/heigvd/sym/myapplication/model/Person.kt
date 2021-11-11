package ch.heigvd.sym.myapplication.model

import ch.heigvd.sym.myapplication.DirectoryOuterClass
import com.google.gson.Gson
import org.xmlpull.v1.XmlPullParser

data class Person(
    var name: String,
    var firstname: String,
    var phones: MutableList<Phone> = emptyList<Phone>().toMutableList(),
    var middle_name: String? = null
) {

    /**
     * Serialize Person as protocol Buffer
     */
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
    companion object {
        fun deserializeProtoBuf(person : DirectoryOuterClass.Person) : Person {
            val name = person.name
            val firstname = person.firstname
            val phones: MutableList<Phone> = emptyList<Phone>().toMutableList()
            for (phone in person.phoneList){
                phones.add(Phone.deserializeProtoBuf(phone))
            }
            if (person.middlename != null && person.middlename != "") {
                return Person( name, firstname, phones, person.middlename)
            }
            return Person(name, firstname, phones)
        }

        fun deserializeJSON(json : String) : Person? {
            return Gson().fromJson(json, Person::class.java)
        }

        fun deserializePersonAndPhonesXML(xmlPullParser: XmlPullParser): Person? {
            var name = ""
            var middlename: String? = ""
            var firstname = ""
            var phoneType = ""
            var text = ""
            val phones = emptyList<Phone>().toMutableList()
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                when (xmlPullParser.eventType) {
                    XmlPullParser.START_TAG ->
                        if (xmlPullParser.name == "phone") {
                            phoneType = xmlPullParser.getAttributeValue(null, "type")
                        }
                    XmlPullParser.TEXT -> text = xmlPullParser.text
                    XmlPullParser.END_TAG ->
                        when (xmlPullParser.name) {
                            "name" -> name = text
                            "firstname" -> firstname = text
                            "middlename" -> middlename = text
                            "phone" -> phones.add(Phone(text, phoneType))
                            "person" -> return Person(
                                name,
                                firstname,
                                phones,
                                middlename
                            )
                        }
                }
            }
            return null
        }
    }
}