package ch.heigvd.sym.myapplication.model

/*
Author class with id and name
 */
data class Author(val id: Int, val name: String) {
    @Override
    override fun toString(): String {
        return this.name
    }
}