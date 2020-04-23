package com.syaphen.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    private val activityTitle = "All Songs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        title = activityTitle
        val listOfSongs = mutableListOf<Song>()
        listOfSongs.addAll(SongDataProvider.getAllSongs())
        // Create adapter (may want to save it as property)

        val songListAdapter = SongListAdapter(listOfSongs)

        shuffleButton.setOnClickListener {
            val newListOfSongs = listOfSongs.apply {
                shuffle()
            }
            songListAdapter.change(newListOfSongs)
        }
        songListRecyclerView.adapter = songListAdapter
    }
}
