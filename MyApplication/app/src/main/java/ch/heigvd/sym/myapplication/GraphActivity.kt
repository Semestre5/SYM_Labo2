package ch.heigvd.sym.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import org.json.JSONObject
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

        val symComManager = SymComManager(this)
        symComManager.sendRequest("http://mobile.iict.ch/graphql", "{\"query\":\"{findAllAuthors{id, name}}\"}")

    }

    override fun handleServerResponse(response: String) {
        val reader = JSONObject(response)
        val data = reader.getJSONObject("data")
        val authors = data.getJSONArray("findAllAuthors")
        for (i in 0 until authors.length()) {
            val author = authors.getJSONObject(i)
            val a = Author(
                author.getInt("id"),
                author.getString("name")
            )
            listAuthors.add(a)
        }
        adapterAuthor.notifyDataSetChanged()
    }
}

data class Author(val id: Int, val name: String) {
    override fun toString(): String {
        return this.name
    }
}