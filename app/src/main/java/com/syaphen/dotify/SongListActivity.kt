package com.syaphen.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.syaphen.dotify.SongPlayerActivity.Companion.SONG_KEY
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    private val activityTitle = "All Songs"
    private lateinit var songPlaying: Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        title = activityTitle

        // Fetch song list
        val listOfSongs = mutableListOf<Song>()
        listOfSongs.addAll(SongDataProvider.getAllSongs())

        // Create adapter (may want to save it as property)
        val songListAdapter = SongListAdapter(listOfSongs)

        // Set on item Click for the adapter
        songListAdapter.onSongClickListener = { currentSong: Song ->
            val displayedInfo = currentSong.title + " - " + currentSong.artist
            song_info_miniPlayer.text = displayedInfo
            songPlaying = currentSong
        }

        miniPlayer.setOnClickListener {
            val intent = Intent(this, SongPlayerActivity::class.java)
            intent.putExtra(SONG_KEY, songPlaying)
            startActivity(intent)
        }

        // Shuffle feature
        shuffleButton.setOnClickListener {
            val newListOfSongs = listOfSongs.apply {
                shuffle()
            }
            songListAdapter.change(newListOfSongs)
        }

        // Connect adapter
        songListRecyclerView.adapter = songListAdapter
    }
}
