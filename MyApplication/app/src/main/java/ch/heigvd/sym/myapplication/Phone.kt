package ch.heigvd.sym.myapplication

data class Phone(
    var phone: String,
    var typephone: String,
) {
    fun serializeProtoBuf(): DirectoryOuterClass.Phone? {
        val phoneBuildConfig = DirectoryOuterClass.Phone.newBuilder()
        phoneBuildConfig.number = this.phone
        when(typephone) {
            "home" -> phoneBuildConfig.type = DirectoryOuterClass.Phone.Type.HOME
            "work" -> phoneBuildConfig.type = DirectoryOuterClass.Phone.Type.WORK
            "mobile" -> phoneBuildConfig.type = DirectoryOuterClass.Phone.Type.MOBILE
        }
        return phoneBuildConfig.build()
    }

    override fun toString(): String {
        return "phone : " + phone + " (" + this.typephone + ") "
    }

    companion object {
        private fun getTypeFromBuilderType(builderType: DirectoryOuterClass.Phone.Type) : String {
            return when(builderType){
                DirectoryOuterClass.Phone.Type.HOME -> "home"
                DirectoryOuterClass.Phone.Type.WORK -> "work"
                DirectoryOuterClass.Phone.Type.MOBILE -> "mobile"
                else -> "error"
            }
        }

        fun deserializeProtoBuf(phone : DirectoryOuterClass.Phone) : Phone {
            return Phone(phone.number, getTypeFromBuilderType(phone.type))
        }
    }
}