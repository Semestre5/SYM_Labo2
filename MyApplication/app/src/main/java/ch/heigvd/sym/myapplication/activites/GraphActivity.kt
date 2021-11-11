package ch.heigvd.sym.myapplication.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import ch.heigvd.sym.myapplication.R
import ch.heigvd.sym.myapplication.Utils
import ch.heigvd.sym.myapplication.communication.CommunicationEventListener
import ch.heigvd.sym.myapplication.communication.SymComManager
import ch.heigvd.sym.myapplication.model.Author
import org.json.JSONObject

class GraphActivity : CommunicationEventListener, AppCompatActivity() {

    private lateinit var authors: Spinner
    private lateinit var books: ListView
    private val listAuthors = ArrayList<Author>()
    private val listBooks = ArrayList<String>()
    private lateinit var adapterAuthor: ArrayAdapter<Author>
    private lateinit var adapterBook: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        // Sett the variables to get the items on view and create the adapters
        authors = findViewById(R.id.spinnerAuthor)
        books = findViewById(R.id.listViewBooks)
        adapterAuthor =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listAuthors)
        adapterBook = ArrayAdapter(this, android.R.layout.simple_list_item_1, listBooks)
        authors.adapter = adapterAuthor
        books.adapter = adapterBook

        val symComManager = SymComManager(this)

        // Set the event when we select an author
        authors.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Send request with good id of author when we select an author
                symComManager.sendRequest(
                    Utils.URL_GRAPHQL,
                    ("{\"query\":\"{findAuthorById(id: " + listAuthors[position].id +
                            "){books{title}}}\"}\"}").toByteArray(),
                    Utils.CONTENT_JSON)
            }
        }

        // Send the request to fill the spinner with the authors
        symComManager.sendRequest(Utils.URL_GRAPHQL,
            ("{\"query\":\"{findAllAuthors{id, name}}\"}").toByteArray(),
            Utils.CONTENT_JSON)
    }

    override fun handleServerResponse(response: String) {
        val reader = JSONObject(response)
        val data = reader.getJSONObject("data")

        // Get response to fill the spinner with the authors
        if(response.contains("findAllAuthors")){
            listAuthors.clear()
            val authors = data.getJSONArray("findAllAuthors")
            for (i in 0 until authors.length()) {
                val author = authors.getJSONObject(i)
                val a = Author(author.getInt("id"), author.getString("name"))
                listAuthors.add(a)
            }
            adapterAuthor.notifyDataSetChanged()
        // Get response to fill the listView with the books of the author
        } else {
            listBooks.clear()
            val books = data.getJSONObject("findAuthorById").getJSONArray("books")
            for (i in 0 until books.length()) {
                val book = books.getJSONObject(i)
                listBooks.add(book.getString("title"))
            }
            adapterBook.notifyDataSetChanged()
        }
    }


}