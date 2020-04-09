package com.syaphen.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val defaultUsername = "Anonymous User"
    private var currentUsername = "Not logged in"
    private var playCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usernameDisplay.text = defaultUsername
        playCountDisplay.text = "$playCount Plays"
        changeBtn.setOnClickListener { v: View ->
            promptUsernameMenu(v)
        }
        submitBtn.setOnClickListener { v: View ->
            updateUsername(v)
        }
        playBtn.setOnClickListener { v: View ->
            incPlayCount(v)
        }
    }

    private fun incPlayCount(view: View) {
        playCount += 1
        playCountDisplay.text = "${playCount+1} Plays"
    }

    private fun promptUsernameMenu(view: View) {
        usernameInput.visibility = View.VISIBLE
        submitBtn.visibility = View.VISIBLE
        changeBtn.visibility = View.GONE
    }

    fun updateUsername(view: View) {
        Log.i("syaphen", "username changed to: ")
        submitBtn.visibility = View.GONE
        changeBtn.visibility = View.VISIBLE
        usernameInput.visibility = View.GONE
        currentUsername = usernameInput.text.toString()
        usernameInput.text.clear()
        usernameDisplay.text = currentUsername

        val myMsg = """
            Hello $currentUsername
        """.trimIndent()
        Toast.makeText(this, myMsg, Toast.LENGTH_SHORT).show()
    }
}
