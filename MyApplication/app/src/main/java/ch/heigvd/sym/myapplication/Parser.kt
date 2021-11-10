package ch.heigvd.sym.myapplication

import com.google.gson.Gson
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory

class Parser {
    fun deserializeJSON(json : String) : Person? {
        return Gson().fromJson(json, Person::class.java)
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
                    "person" -> directory.addPerson(deserializePerson(parser))
                }
            }
        }
        return directory;
    }

    private fun deserializePerson(xmlPullParser: XmlPullParser): Person? {
        var name: String = ""
        var middlename: String? = ""
        var mail: String = ""
        var firstname: String = ""
        var phone: String = ""
        var phoneType: String = ""
        var text: String = ""
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
            when (xmlPullParser.getEventType()) {
                XmlPullParser.START_TAG -> if (xmlPullParser.name == "phone"){
                    phoneType = xmlPullParser.getAttributeValue(null, "type");
                }
                XmlPullParser.TEXT -> text = xmlPullParser.text
                XmlPullParser.END_TAG ->
                    when (xmlPullParser.name) {
                        "name" -> name = text
                        "firstname" -> firstname = text
                        "middlename" -> middlename = text
                        "phone" -> phone = text
                        "mail" -> mail = text
                        "person" -> return Person(
                            name,
                            firstname,
                            mail,
                            phone,
                            phoneType,
                            middlename
                    )
                }
            }
        }
        return null;
    }


}