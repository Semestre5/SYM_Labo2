package ch.heigvd.sym.myapplication

import com.google.gson.Gson
import android.util.Xml
import java.io.StringWriter
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

data class Person (
    var name: String,
    var firstname: String,
    var mail: String,
    var phone: String,
    var typephone: String? = null,
    var middle_name : String? = null
) {
    /*
    enum PhoneType {
        MOBILE = 0;
        HOME = 1;
        WORK = 2;
    }*/


    fun serializeXML(): String {
        val xmlSerializer = Xml.newSerializer()
        val writer = StringWriter()

        xmlSerializer.setOutput(writer)
        xmlSerializer.startDocument("UTF-8", false)
        xmlSerializer.docdecl(" directory SYSTEM \"http://mobile.iict.ch/directory.dtd\"");


        xmlSerializer.startTag("", "directory")
        xmlSerializer.startTag("", "person")

        // name
        xmlSerializer.startTag("", "name")
        xmlSerializer.text(this.name)
        xmlSerializer.endTag("", "name")

        xmlSerializer.startTag("", "firstname")
        xmlSerializer.text(this.firstname)
        xmlSerializer.endTag("", "firstname")

        if (this.middle_name != null && this.middle_name != "") {
            xmlSerializer.startTag("", "middlename")
            xmlSerializer.text(this.middle_name)
            xmlSerializer.endTag("", "middlename")
        }

        xmlSerializer.startTag("", "phone")
        xmlSerializer.attribute("", "type", this.typephone)
        xmlSerializer.text(this.phone)
        xmlSerializer.endTag("", "phone")

        //end tag <file>
        xmlSerializer.endTag("", "person")
        xmlSerializer.endTag("", "directory")
        xmlSerializer.endDocument()

        return writer.toString()
    }

    fun serializeProtoBuf(): String {
        TODO("Not yet implemented")
    }

    fun deserializeProtoBuf() {
        TODO("Not yet implemented")
    }

    fun serializeJSON(): String {
        return Gson().toJson(this);
    }
}