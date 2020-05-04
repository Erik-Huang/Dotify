package com.syaphen.dotify.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericchee.songdataprovider.Song
import com.syaphen.dotify.OnSongClickListener

import com.syaphen.dotify.R
import com.syaphen.dotify.SongListAdapter
import kotlinx.android.synthetic.main.fragment_song_list.*


class SongListFragment : Fragment() {

    private lateinit var songListAdapter: SongListAdapter
    private var onSongClickListener: OnSongClickListener? = null
    private lateinit var songList: List<Song>

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        const val SONG_LIST = "song_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var list: List<Song>? = null
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                list = getParcelableArrayList(SONG_LIST)
            }
        } else {
            arguments?.let { args ->
                list = args.getParcelableArrayList(SONG_LIST)
            }
        }
        if (list != null) {
            songList = list as List<Song>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(SONG_LIST, ArrayList(songList))
    }

    fun shuffleSongList() {
        songList = songList.shuffled()
        songListAdapter.change(songList)
        songListRecyclerView.scrollToPosition(0)
    }

}