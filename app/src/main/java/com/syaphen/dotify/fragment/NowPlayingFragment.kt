package com.syaphen.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ericchee.songdataprovider.Song

import com.syaphen.dotify.R
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlin.random.Random

class NowPlayingFragment : Fragment() {

    private lateinit var songPlaying: Song
    private var playCount = 0

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val SONG_KEY = "song_key"
        const val PLAY_COUNT = "play_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                val song: Song? = getParcelable(SONG_KEY)
                if (song != null) {
                    songPlaying = song
                }
                playCount = getInt(PLAY_COUNT)
            }
        } else {
            arguments?.let { args ->
                val song = args.getParcelable<Song>(SONG_KEY)
                if (song != null) {
                    songPlaying = song
                }
                playCount = Random.nextInt(0, 500)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    fun updateSong(song: Song) {
        songPlaying = song
        albumTitle.text = song.title
        albumDescription.text = song.artist
        playCountDisplay.text = "$playCount Plays"
        albumDisplay.setImageResource(song.largeImageID)
    }

    private fun initUI() {
        albumTitle.text = songPlaying.title
        albumDescription.text = songPlaying.artist
        playCountDisplay.text = "$playCount Plays"
        albumDisplay.setImageResource(songPlaying.largeImageID)
        playBtn.setOnClickListener { v: View ->
            incPlayCount(v)
        }
        prevBtn.setOnClickListener { v: View ->
            changeTrack(v, "Skipping to previous track")
        }
        nextBtn.setOnClickListener { v: View ->
            changeTrack(v, "Skipping to next track")
        }
    }

    private fun incPlayCount(view: View) {
        playCount += 1
        playCountDisplay.text = "$playCount Plays"
    }

    private fun changeTrack(view: View, msg: String) {
        // TODO
    }
}
