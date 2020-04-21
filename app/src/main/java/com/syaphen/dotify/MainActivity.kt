package com.syaphen.dotify

import android.graphics.Color
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
    private val albumTitleTemp = "Dark Souls III OST"
    private val albumDescTemp = "Dark Souls 3 Official OST - Gamescom 2015 Trailer Music"
    private var playCount = Random.nextInt(100, 500)
    private val change = 10

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
        albumDisplay.setOnLongClickListener{ v: View ->
            changeColor(v)
            return@setOnLongClickListener true
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

    private fun updateUsername(view: View) {
        changeBtn.text = "CHANGE USER"
        changeBtn.setOnClickListener { v: View ->
            promptUsernameMenu(v)
        }
        usernameInput.visibility = View.GONE
        usernameDisplay.visibility = View.VISIBLE
        currentUsername = usernameInput.text.toString()
        usernameInput.text.clear()
        if (currentUsername == "") {
            Toast.makeText(this, "Invalid Username!", Toast.LENGTH_SHORT).show()
        } else {
            usernameDisplay.text = currentUsername
            Toast.makeText(this, "Hello $currentUsername", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeColor(view: View) {
        val c = Color.parseColor("#0aad3f") // Green tint
        playCountDisplay.setTextColor(c)
        albumTitle.setTextColor(c)
        albumDescription.setTextColor(c)
        usernameInput.setTextColor(c)
        usernameInput.setHintTextColor(c)
        usernameDisplay.setTextColor(c)
        changeBtn.setTextColor(c)
        nextBtn.setColorFilter(c)
        prevBtn.setColorFilter(c)
    }
}
