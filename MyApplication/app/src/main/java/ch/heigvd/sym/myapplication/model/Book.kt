package ch.heigvd.sym.myapplication.model

data class Book(val title: String) {
    override fun toString(): String {
        return this.title
    }
}