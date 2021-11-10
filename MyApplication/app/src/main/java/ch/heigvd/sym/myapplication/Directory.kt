package ch.heigvd.sym.myapplication

class Directory (
    var persons: MutableList<Person> = emptyList<Person>().toMutableList()
)
{
    fun addPerson(person: Person?){
        if (person != null)
            persons.add(person)
    }
}