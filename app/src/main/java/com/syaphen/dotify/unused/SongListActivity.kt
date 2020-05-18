package com.syaphen.dotify.unused

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.syaphen.dotify.R
import com.syaphen.dotify.SongListAdapter
import com.syaphen.dotify.unused.SongPlayerActivity.Companion.SONG_KEY
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    private val activityTitle = "All Songs"
    private var songPlaying: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        title = activityTitle

        // Fetch song list
        val listOfSongs: MutableList<Song> = mutableListOf<Song>()
        listOfSongs.addAll(SongDataProvider.getAllSongs())

        // Create adapter (may want to save it as property)
        val songListAdapter =
            SongListAdapter(listOfSongs)

        // Set on item Click for the adapter
        songListAdapter.onSongClickListener = { currentSong: Song ->
            val displayedInfo = currentSong.title + " - " + currentSong.artist
            song_info_miniPlayer.text = displayedInfo

            songPlaying = currentSong
        }

        // Delete item on long press
        songListAdapter.onSongLongPressListener = { currentSong: Song ->
            Toast.makeText(this, "${currentSong.title} removed", Toast.LENGTH_SHORT).show()
            listOfSongs.remove(currentSong)
            songListAdapter.change(listOfSongs)
        }

        // Navigating to the main player activity
        miniPlayer.setOnClickListener {
            if (songPlaying != null) {
                val intent = Intent(this, SongPlayerActivity::class.java)
                intent.putExtra(SONG_KEY, songPlaying)
                startActivity(intent)
            }
        }

        // Shuffle feature
        shuffleButton.setOnClickListener {
            var newListOfSongs = listOfSongs.apply { shuffle() }
            songListAdapter.change(newListOfSongs)
            songListRecyclerView.scrollToPosition(0)
        }

        // Connect adapter
        songListRecyclerView.adapter = songListAdapter
    }
}
