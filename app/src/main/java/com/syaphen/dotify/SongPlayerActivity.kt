package com.syaphen.dotify

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_song_list.*
import kotlin.random.Random

class SongPlayerActivity : AppCompatActivity() {

    private val defaultUsername = "Anonymous User"
    private var currentUsername = "Not logged in"
    private val albumTitleTemp = "Dark Souls III OST"
    private val albumDescTemp = "Dark Souls 3 Official OST - Gamescom 2015 Trailer Music"
    private var playCount = Random.nextInt(100, 500)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        // Grab data from Intent's Extras
        val songPlaying = intent.getParcelableExtra<Song>(SONG_KEY)
        Log.i("erik", songPlaying.title)
        Log.i("erik", songPlaying.largeImageID.toString())
        albumTitle.text = songPlaying.title
        albumDescription.text = songPlaying.artist
        albumDisplay.setImageResource(songPlaying.largeImageID)
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
            changeTrack(v, "Skipping to previous track")
        }
        nextBtn.setOnClickListener { v: View ->
            changeTrack(v, "Skipping to next track")
        }
        albumDisplay.setOnLongClickListener{ v: View ->
            changeColor(v)
            return@setOnLongClickListener true
        }
    }

    private fun changeTrack(view: View, msg: String) {
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

    companion object {
        // Keys for intents
        const val SONG_KEY = "song_key"
    }
}
