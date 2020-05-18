package com.syaphen.dotify.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericchee.songdataprovider.Song
import com.syaphen.dotify.manager.OnSongClickListener

import com.syaphen.dotify.R
import com.syaphen.dotify.SongListAdapter
import com.syaphen.dotify.DotifyApp
import com.syaphen.dotify.manager.MusicManager
import kotlinx.android.synthetic.main.fragment_song_list.*


class SongListFragment : Fragment() {

    private lateinit var songListAdapter: SongListAdapter
    private var onSongClickListener: OnSongClickListener? = null
    private lateinit var songList: List<Song>

    private lateinit var musicManager: MusicManager

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName

        fun getInstance(): SongListFragment {
            return SongListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        musicManager = (context.applicationContext as DotifyApp).musicManager
        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    // Fetch list of songs from the music manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songList = musicManager.listOfSongs
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Create and connect the adapter for the recycler view
        songListAdapter = SongListAdapter(this.songList)
        songListRecyclerView.adapter = songListAdapter
        songListAdapter.onSongClickListener = { currentSong ->
            onSongClickListener?.onSongClicked(currentSong)
        }
    }

    fun shuffleSongList() {
        musicManager.shuffle()
        songList = musicManager.listOfSongs
        songListAdapter.change(songList)
        songListRecyclerView.scrollToPosition(0)
    }

}