package ch.heigvd.sym.myapplication

data class Book(val title: String) {
    override fun toString(): String {
        return this.title
    }
}