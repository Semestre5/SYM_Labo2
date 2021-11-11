package ch.heigvd.sym.myapplication

data class Author(val id: Int, val name: String) {
    override fun toString(): String {
        return this.name
    }
}