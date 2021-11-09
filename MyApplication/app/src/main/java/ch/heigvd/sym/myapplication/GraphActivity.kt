package ch.heigvd.sym.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.net.CacheResponse

class GraphActivity : CommunicationEventListener, AppCompatActivity() {

    private lateinit var authors: Spinner
    val listAuthors = ArrayList<Author>()
    lateinit var adapterAuthor: ArrayAdapter<Author>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        authors = findViewById(R.id.spinnerAuthor)
        adapterAuthor = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listAuthors)
        authors.adapter = adapterAuthor

        val sm = SymComManager(this)S

    }

    override fun handleServerResponse(response: String) {

    }
}

data class Author(val id: Int, val first_name: String, val last_name: String) {
    override fun toString(): String {
        return this.first_name + " " + this.last_name
    }
}