package ch.heigvd.sym.myapplication.model

import ch.heigvd.sym.myapplication.DirectoryOuterClass
import ch.heigvd.sym.myapplication.Utils
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlSerializer
import java.lang.StringBuilder

/**
 * Authors : Axel Vallon, Lev Pozniakoff and Robin Gaudin
 * Date : 12.11.2021
 * Person: Person class, with Parser and Serializer for XML and Protocol Buffer
 */

data class Person(
    var name: String,
    var firstname: String,
    var phones: MutableList<Phone> = emptyList<Phone>().toMutableList(),
    var middle_name: String? = null
) {

    /**
     * Serialize Person in protocol Buffer
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

    /**
     * Serialize Person in protocol Buffer, must be called from Directory
     */
    fun serializeXML(xmlSerializer : XmlSerializer) {
        xmlSerializer.startTag("", Utils.TAG_PERSON)

        xmlSerializer.startTag("", Utils.TAG_NAME)
        xmlSerializer.text(name)
        xmlSerializer.endTag("", Utils.TAG_NAME)

        xmlSerializer.startTag("", Utils.TAG_FIRSTNAME)
        xmlSerializer.text(firstname)
        xmlSerializer.endTag("", Utils.TAG_FIRSTNAME)

        if (middle_name != null && middle_name != "") {
            xmlSerializer.startTag("", Utils.TAG_MIDDLE_NAME)
            xmlSerializer.text(middle_name)
            xmlSerializer.endTag("", Utils.TAG_MIDDLE_NAME)
        }

        for (phone in phones) {
            phone.serializeXML(xmlSerializer)
        }

        xmlSerializer.endTag("", Utils.TAG_PERSON)
    }


    @Override
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("name : " + this.name + System.getProperty("line.separator"))
        if (middle_name != null && middle_name != "")
            sb.append("middle name : "+ this.middle_name + System.getProperty("line.separator"))
        sb.append("firstname : " + this.firstname)
        for (phone in phones) {
            sb.append(System.getProperty("line.separator"))
            sb.append(phone.toString())
        }
        return sb.toString()
    }

    companion object {
        /**
        * Static method to deserialize a Person from Protocol Buffer format
        **/
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

        /**
         * Static method to deserialize a Person and their Phone from XML format
         **/
        fun deserializePersonAndPhonesXML(xmlPullParser: XmlPullParser): Person? {
            // tmp to store text observed in parsing
            var name = ""
            var middlename: String? = ""
            var firstname = ""
            var phoneType = ""
            var text = ""
            val phones = emptyList<Phone>().toMutableList()
            // this while condition is a security if the person tag is not closed
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                when (xmlPullParser.eventType) {
                    XmlPullParser.START_TAG ->
                        // phone identified, we save the mandatory attribute to get the text later
                        if (xmlPullParser.name == Utils.TAG_PHONE) {
                            phoneType = xmlPullParser.getAttributeValue(null, Utils.TAG_TYPE)
                        }

                    // we save the text for the end tag associated
                    XmlPullParser.TEXT -> text = xmlPullParser.text

                    // closing of a xml tag, 2 distinct cases
                    // if it's a person, we return the person
                    // else, we save it until the coming closure tag of person to return it
                    XmlPullParser.END_TAG ->
                        when (xmlPullParser.name) {
                            Utils.TAG_NAME -> name = text
                            Utils.TAG_FIRSTNAME -> firstname = text
                            Utils.TAG_MIDDLE_NAME -> middlename = text
                            Utils.TAG_PHONE -> phones.add(Phone(text, phoneType))
                            Utils.TAG_PERSON -> return Person(
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