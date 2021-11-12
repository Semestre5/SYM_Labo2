package ch.heigvd.sym.myapplication

/**
 * Authors : Axel Vallon, Lev Pozniakoff and Robin Gaudin
 * Date : 12.11.2021
 * Constants file
 */
object Utils {
    const val URL_TXT = "http://mobile.iict.ch/api/txt"
    const val URL_GRAPHQL = "http://mobile.iict.ch/graphql"
    const val URL_JSON = "http://mobile.iict.ch/api/json"
    const val URL_XML = "http://mobile.iict.ch/api/xml"
    const val URL_PROTOBUF = "http://mobile.iict.ch/api/protobuf"

    const val CONTENT_JSON = "application/json"
    const val CONTENT_XML = "application/xml"
    const val CONTENT_PROTOBUF = "application/protobuf"

    const val DEFERRED_TIMING : Long = 10000

    const val XML_DTD = " directory SYSTEM \"http://mobile.iict.ch/directory.dtd\""
    const val TAG_DIRECTORY = "directory"
    const val TAG_PERSON = "person"
    const val TAG_NAME = "name"
    const val TAG_FIRSTNAME = "firstname"
    const val TAG_MIDDLE_NAME = "middlename"
    const val TAG_PHONE = "phone"
    const val TAG_TYPE = "type"
    const val TAG_TYPE_HOME = "home"
    const val TAG_TYPE_WORK = "work"
    const val TAG_TYPE_MOBILE = "mobile"

}
