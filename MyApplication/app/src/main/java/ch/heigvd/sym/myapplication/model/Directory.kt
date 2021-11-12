package ch.heigvd.sym.myapplication.model

import android.util.Xml
import ch.heigvd.sym.myapplication.DirectoryOuterClass
import ch.heigvd.sym.myapplication.Utils
import com.google.gson.Gson
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringWriter
import java.lang.StringBuilder

/**
 * Authors : Axel Vallon, Lev Pozniakoff and Robin Gaudin
 * Date : 12.11.2021
 * Directory: class with Parser and Serializer for JSON, XML and Protocol Buffer
 */

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
     * Serialize from String format in JSON
     */
    fun serializeJSON(): String {
        return Gson().toJson(this)
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
        // document xml initialization

        val xmlSerializer = Xml.newSerializer()
        val writer = StringWriter()
        xmlSerializer.setOutput(writer)
        xmlSerializer.startDocument("UTF-8", false)
        xmlSerializer.docdecl(Utils.XML_DTD)

        xmlSerializer.startTag("", Utils.TAG_DIRECTORY)
        // add all Person in Directory
        for (person in persons) {
            person.serializeXML(xmlSerializer)
        }
        xmlSerializer.endTag("", Utils.TAG_DIRECTORY)
        xmlSerializer.endDocument()
        return writer.toString()
    }

    @Override
    override fun toString(): String {
        val sb = StringBuilder()
        for (person in persons) {
            sb.append(person.toString())
            sb.append(System.getProperty("line.separator"))
        }
        return sb.toString()
    }

    companion object {
        /**
         * Static method to deserialize a Directory from Protocol Buffer format
         */
        fun deserializeProtoBuf(response: String): Directory {
            val directoryBuilder = DirectoryOuterClass.Directory.parseFrom(response.toByteArray())
            val directory = Directory()
            for (person in directoryBuilder.resultsList){
                directory.addPerson(Person.deserializeProtoBuf(person))
            }
            return directory
        }

        /**
         * Static method to deserialize a Directory from JSON format
         */
        fun deserializeJSON(json : String) : Directory? {
            return Gson().fromJson(json, Directory::class.java)
        }

        /**
         * Static method to deserialize a Directory from XML format
         */
        fun deserializeXML(xml : String) : Directory {
            val directory = Directory()

            // creation of XML parser
            val parserFactory = XmlPullParserFactory.newInstance()
            val parser = parserFactory.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(xml.byteInputStream(), null)

            // parse all XML document
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                when (parser.eventType) {
                    // Person encountered, Person class do the job for each Person
                    XmlPullParser.START_TAG -> when (parser.name) {
                        Utils.TAG_PERSON -> directory.addPerson(Person.deserializePersonAndPhonesXML(parser))
                    }
                }
            }
            return directory
        }
    }
}