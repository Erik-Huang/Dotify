package com.syaphen.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val defaultUsername = "Anonymous User"
    private var currentUsername = "Not logged in"
    private val albumTitleTemp = "Strangers"
    private val albumDescTemp = "\"Strangers\" (with Myon & Shane 54 featuring Tove Lo)"
    private var playCount = Random.nextInt(100, 500)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        albumTitle.text = albumTitleTemp
        albumDescription.text = albumDescTemp
        usernameDisplay.text = defaultUsername
        playCountDisplay.text = "$playCount Plays"
        changeBtn.setOnClickListener { v: View ->
            promptUsernameMenu(v)
        }
        playBtn.setOnClickListener { v: View ->
            incPlayCount(v)
        }
        prevBtn.setOnClickListener { v: View ->
            previousTrack(v)
        }
        nextBtn.setOnClickListener { v: View ->
            nextTrack(v)
        }
    }

    private fun previousTrack(view: View) {
        val msg = "Skipping to previous track"
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun nextTrack(view: View) {
        val msg = "Skipping to next track"
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun incPlayCount(view: View) {
        playCount += 1
        playCountDisplay.text = "$playCount Plays"
    }

    private fun promptUsernameMenu(view: View) {
        changeBtn.text = "APPLY"
        changeBtn.setOnClickListener { v: View ->
            updateUsername(v)
        }
        usernameInput.visibility = View.VISIBLE
        usernameDisplay.visibility = View.GONE
    }

    fun updateUsername(view: View) {
        changeBtn.text = "CHANGE USER"
        changeBtn.setOnClickListener { v: View ->
            promptUsernameMenu(v)
        }
        usernameInput.visibility = View.GONE
        usernameDisplay.visibility = View.VISIBLE
        currentUsername = usernameInput.text.toString()
        usernameInput.text.clear()
        usernameDisplay.text = currentUsername

        val myMsg = "Hello $currentUsername"
        Toast.makeText(this, myMsg, Toast.LENGTH_SHORT).show()
    }
}
