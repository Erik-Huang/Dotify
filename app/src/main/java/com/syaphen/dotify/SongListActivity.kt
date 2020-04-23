package com.syaphen.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    private val activityTitle = "All Songs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        title = activityTitle
        val listOfSongs = SongDataProvider.getAllSongs()
        // Create adapter (may want to save it as property)
        val listOfPeople = listOf("Spongebob", "Squidward", "Eric Chee", "Clint", "Thanos was right", "Stark", "Chortle", "Patrick", "Dr. Oz", "Baby Yoda", "Dave", "null", "pointer", "This lecture sucks yoooo wtf is a list")

        val songListAdapter = SongListAdapter(listOfSongs)
        songListRecyclerView.adapter = songListAdapter
    }
}
