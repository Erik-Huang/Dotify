package com.syaphen.dotify.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.syaphen.dotify.DotifyApp

import com.syaphen.dotify.R
import com.syaphen.dotify.manager.MusicManager
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlin.random.Random

class NowPlayingFragment : Fragment() {

    private lateinit var songPlaying: Song

    private lateinit var musicManager: MusicManager

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName

        fun getInstance(): NowPlayingFragment {
            return NowPlayingFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        musicManager = (context.applicationContext as DotifyApp).musicManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (musicManager.songPlaying != null) {
            songPlaying = musicManager.songPlaying!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        albumTitle.text = songPlaying.title
        albumDescription.text = songPlaying.artist
        playCountDisplay.text = "${musicManager.playCount} Plays"
        albumDisplay.setImageResource(songPlaying.largeImageID)
        playBtn.setOnClickListener { v: View ->
            incPlayCount(v)
        }
        prevBtn.setOnClickListener { v: View ->
            changeTrack(v, false)
        }
        nextBtn.setOnClickListener { v: View ->
            changeTrack(v, true)
        }
    }

    private fun incPlayCount(view: View) {
        musicManager.playCount += 1
        playCountDisplay.text = "${musicManager.playCount} Plays"
    }

    private fun changeTrack(view: View, isNext: Boolean) {
        var direction = if (isNext) 1 else -1
        val currSongIndex = musicManager.listOfSongs.indexOf(songPlaying)
        val nextSong = musicManager.listOfSongs[currSongIndex + direction]
        musicManager.songPlaying = nextSong
        songPlaying = nextSong
        initUI()
    }

}
