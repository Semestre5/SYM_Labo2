package ch.heigvd.sym.myapplication.model

import android.util.Xml
import ch.heigvd.sym.myapplication.DirectoryOuterClass
import com.google.gson.Gson
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringWriter
import java.lang.StringBuilder

class Directory (
    private var persons: MutableList<Person> = emptyList<Person>().toMutableList()
) {
    /**
     * Add a person to this directory of person
     */
    fun addPerson(person: Person?){
        if (person != null)
            persons.add(person)
    }

    /**
     * Serialize this Directory as a Protocol Buffer
     */
    fun serializeProtoBuf(): ByteArray {
        val directory = DirectoryOuterClass.Directory.newBuilder()
        for (person in persons) {
            directory.addResults(person.serializeProtoBuf())
        }
        return directory.build().toByteArray()
    }

    /**
     * Serialize this Directory in XML format
     */
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

    companion object {
        fun deserializeProtoBuf(response: String): Directory {
            val directoryBuilder = DirectoryOuterClass.Directory.parseFrom(response.toByteArray())
            val directory = Directory()
            for (person in directoryBuilder.resultsList){
                directory.addPerson(Person.deserializeProtoBuf(person))
            }
            return directory;
        }

        fun deserializeJSON(json : String) : Directory? {
            return Gson().fromJson(json, Directory::class.java)
        }

        fun deserializeXML(xml : String) : Directory {
            val directory = Directory()
            val parserFactory = XmlPullParserFactory.newInstance()
            val parser = parserFactory.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(xml.byteInputStream(), null)
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                when (parser.eventType) {
                    XmlPullParser.START_TAG -> when (parser.name) {
                        "person" -> directory.addPerson(Person.deserializePersonAndPhonesXML(parser))
                    }
                }
            }
            return directory;
        }
    }
}