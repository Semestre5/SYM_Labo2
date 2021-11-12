package ch.heigvd.sym.myapplication.model

import ch.heigvd.sym.myapplication.DirectoryOuterClass
import ch.heigvd.sym.myapplication.Utils
import org.xmlpull.v1.XmlSerializer

/**
 * Authors : Axel Vallon, Lev Pozniakoff and Robin Gaudin
 * Date : 12.11.2021
 * Phone class, with Parser and Serializer for Protocol Buffer
 */
data class Phone(
    var phone: String,
    var type: String,
) {
    /**
     * Serialize Phone in protocol Buffer
     */
    fun serializeProtoBuf(): DirectoryOuterClass.Phone? {
        val phoneBuildConfig = DirectoryOuterClass.Phone.newBuilder()
        phoneBuildConfig.number = this.phone
        phoneBuildConfig.type = getBuilderTypeFromType()
        return phoneBuildConfig.build()
    }

    /**
     * Serialize Phone in XML, must be called from Person
     */
    fun serializeXML(xmlSerializer : XmlSerializer) {
        xmlSerializer.startTag("", Utils.TAG_PHONE)
        xmlSerializer.attribute("", Utils.TAG_TYPE, type)
        xmlSerializer.text(phone)
        xmlSerializer.endTag("", Utils.TAG_PHONE)
    }

    /**
     * Convert type String value in Protocol Buffer Phone's numeric type
     * If the type is non existent, it return DirectoryOuterClass.Phone.Type.UNRECOGNIZED
     */
    private fun getBuilderTypeFromType() : DirectoryOuterClass.Phone.Type {
        return when (this.type) {
            Utils.TAG_TYPE_HOME -> DirectoryOuterClass.Phone.Type.HOME
            Utils.TAG_TYPE_WORK -> DirectoryOuterClass.Phone.Type.WORK
            Utils.TAG_TYPE_MOBILE -> DirectoryOuterClass.Phone.Type.MOBILE
            else -> DirectoryOuterClass.Phone.Type.UNRECOGNIZED
        }
    }

    @Override
    override fun toString(): String {
        return "phone : " + phone + " (" + this.type + ") "
    }

    companion object {
        /**
         * Convert Protocol Buffer Phone's numeric type in String value
         * If the type is non existent, it return "error"
         */
        private fun getTypeFromBuilderType(builderType: DirectoryOuterClass.Phone.Type) : String {
            //return string associated, else return the text "error"
            return when(builderType){
                DirectoryOuterClass.Phone.Type.HOME -> Utils.TAG_TYPE_HOME
                DirectoryOuterClass.Phone.Type.WORK -> Utils.TAG_TYPE_WORK
                DirectoryOuterClass.Phone.Type.MOBILE -> Utils.TAG_TYPE_MOBILE
                else -> "error"
            }
        }

        /**
        * Static method to deserialize a Phone from XML format
        **/
        fun deserializeProtoBuf(phone : DirectoryOuterClass.Phone) : Phone {
            return Phone(phone.number, getTypeFromBuilderType(phone.type))
        }
    }
}