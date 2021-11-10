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
}