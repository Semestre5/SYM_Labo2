package ch.heigvd.sym.myapplication

import android.util.Xml
import java.io.StringWriter
import java.lang.StringBuilder

class Directory (
    private var persons: MutableList<Person> = emptyList<Person>().toMutableList()
) {
    fun addPerson(person: Person?){
        if (person != null)
            persons.add(person)
    }

    fun serializeProtoBuf(): ByteArray {
        val directory = DirectoryOuterClass.Directory.newBuilder()
        for (person in persons) {
            directory.addResults(person.serializeProtoBuf())
        }
        return directory.build().toByteArray()
    }

    fun serializeXML(): String {
        val xmlSerializer = Xml.newSerializer()
        val writer = StringWriter()

        xmlSerializer.setOutput(writer)
        xmlSerializer.startDocument("UTF-8", false)
        xmlSerializer.docdecl(" directory SYSTEM \"http://mobile.iict.ch/directory.dtd\"")

        xmlSerializer.startTag("", "directory")
        for (person in persons) {
            xmlSerializer.startTag("", "person")

            xmlSerializer.startTag("", "name")
            xmlSerializer.text(person.name)
            xmlSerializer.endTag("", "name")

            xmlSerializer.startTag("", "firstname")
            xmlSerializer.text(person.firstname)
            xmlSerializer.endTag("", "firstname")

            if (person.middle_name != null && person.middle_name != "") {
                xmlSerializer.startTag("", "middlename")
                xmlSerializer.text(person.middle_name)
                xmlSerializer.endTag("", "middlename")
            }

            for (phone in person.phones) {
                xmlSerializer.startTag("", "phone")
                xmlSerializer.attribute("", "type", phone.typephone)
                xmlSerializer.text(phone.phone)
                xmlSerializer.endTag("", "phone")
            }

            xmlSerializer.endTag("", "person")
        }
        xmlSerializer.endTag("", "directory")
        xmlSerializer.endDocument()

        return writer.toString()
    }

    @Override
    override fun toString(): String {
        val sb = StringBuilder()
        for (person in persons) {
            sb.append(person.toString())
        }
        return sb.toString()
    }
}