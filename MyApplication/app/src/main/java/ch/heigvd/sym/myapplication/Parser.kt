package ch.heigvd.sym.myapplication

import com.google.gson.Gson
import org.xmlpull.v1.XmlPullParser
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
                    "person" -> directory.addPerson(deserializePersonAndPhones(parser))
                }
            }
        }
        return directory;
    }

    private fun deserializePersonAndPhones(xmlPullParser: XmlPullParser): Person? {
        var name: String = ""
        var middlename: String? = ""
        var firstname: String = ""
        var phone: String = ""
        var phoneType: String = ""
        var text: String = ""
        var phones = emptyList<Phone>().toMutableList()
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
            when (xmlPullParser.eventType) {
                XmlPullParser.START_TAG ->
                    if (xmlPullParser.name == "phone"){
                        phoneType = xmlPullParser.getAttributeValue(null, "type");
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
        return null;
    }

    fun deserializeProtoBuf(response: String): Directory {
        val directoryBuilder = DirectoryOuterClass.Directory.parseFrom(response.toByteArray())
        val directory = Directory()
        for (person in directoryBuilder.resultsList){
            directory.addPerson(deserializePersonProtoBuf(person))
        }
        return directory;
    }

    private fun deserializePersonProtoBuf(person : DirectoryOuterClass.Person) : Person {
        val name = person.name
        val firstname = person.firstname
        var phones: MutableList<Phone> = emptyList<Phone>().toMutableList()
        for (phone in person.phoneList){
            phones.add(deserializePhoneProtoBuf(phone))
        }
        if (person.middlename != null && person.middlename != "") {
            return Person( name, firstname, phones, person.middlename)
        }
        return Person(name, firstname, phones)

    }

    private fun deserializePhoneProtoBuf(phone : DirectoryOuterClass.Phone) : Phone {
        return Phone(phone.number, Phone.getTypeFromBuilderType(phone.type))
    }


}