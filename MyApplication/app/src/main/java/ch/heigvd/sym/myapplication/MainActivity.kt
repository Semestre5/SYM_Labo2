package ch.heigvd.sym.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.sym.myapplication.activites.*
import ch.heigvd.sym.myapplication.databinding.ActivityMainBinding

/**
 * Authors : Axel Vallon, Lev Pozniakoff and Robin Gaudin
 * Date : 12.11.2021
 * MainActivity: Redirect user to different activities
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAsync.setOnClickListener {
            val intent = Intent(this@MainActivity, AsyncronActivity::class.java)
            startActivity(intent)
        }

        binding.buttonDiff.setOnClickListener {
            val intent = Intent(this@MainActivity, DeferredActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSerialization.setOnClickListener {
            val intent = Intent(this@MainActivity, SerializationActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCompressed.setOnClickListener {
            val intent = Intent(this@MainActivity, CompressActivity::class.java)
            startActivity(intent)
        }

        binding.buttonGraphql.setOnClickListener {
            val intent = Intent(this@MainActivity, GraphActivity::class.java)
            startActivity(intent)
        }
    }
}