package com.syaphen.dotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.syaphen.dotify.DotifyApp
import com.syaphen.dotify.manager.OnSongClickListener
import com.syaphen.dotify.R
import com.syaphen.dotify.fragment.*
import com.syaphen.dotify.manager.APIManager
import com.syaphen.dotify.manager.MusicManager
import com.syaphen.dotify.model.Song
import kotlinx.android.synthetic.main.activity_master_main.*

class MasterMainActivity : AppCompatActivity(),
    OnSongClickListener {

    private lateinit var musicManager: MusicManager
    private lateinit var apiManager: APIManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_main)
        musicManager = (application as DotifyApp).musicManager
        apiManager = (application as DotifyApp).apiManager

        // populate the song list on the first launch
        if (getSongListFragment() == null) {
            apiManager.fetchSongs ({ songList ->
                // Initialize song list fragment
                musicManager.listOfSongs = songList
                val songListFragment = SongListFragment.getInstance()
                commitSongListFragment(songListFragment)
            }, {
                Toast.makeText(this, "Failed to fetch the song list", Toast.LENGTH_SHORT).show()
            })
        }

        // Initialize mini player
        initializeMiniPlayer()

        // Handle MiniPlayer & Back Button: switching between fragments
        supportFragmentManager.addOnBackStackChangedListener {
            if(supportFragmentManager.backStackEntryCount > 0) {
                miniPlayer.visibility = GONE
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                miniPlayer.visibility = VISIBLE
                initializeMiniPlayer()
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
        // Handle MiniPlayer & Back Button: Rotating in NowPlayingFragment
        if(supportFragmentManager.backStackEntryCount > 0) {
            miniPlayer.visibility = GONE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    // set listener on the mini Player and the shuffle button
    private fun initializeMiniPlayer() {
        val songPlaying = musicManager.songPlaying
        if (songPlaying != null) {
            val displayedInfo = songPlaying.title + " - " + songPlaying.artist
            song_info_miniPlayer.text = displayedInfo
        }
        miniPlayer.setOnClickListener {
            commitNowPlayingFragment()
        }
        // Shuffle feature
        shuffleButton.setOnClickListener {
            getSongListFragment()?.shuffleSongList()
        }
    }

    private fun getSongListFragment() = supportFragmentManager.findFragmentById(R.id.fragContainer) as? SongListFragment

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    private fun commitSongListFragment(songListFragment: SongListFragment) {
        if (getNowPlayingFragment() == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .commit()
        }
    }

    private fun commitNowPlayingFragment() {
        var nowPlayingFragment = NowPlayingFragment.getInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
            .addToBackStack(NowPlayingFragment.TAG)
            .commit()
    }

    override fun onSongClicked(songClicked: Song) {
        musicManager.toggleSongPlaying(songClicked)
        val displayedInfo = songClicked.title + " - " + songClicked.artist
        song_info_miniPlayer.text = displayedInfo
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }
}
