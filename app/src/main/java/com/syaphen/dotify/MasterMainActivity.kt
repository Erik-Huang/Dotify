package com.syaphen.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import androidx.fragment.app.FragmentTransaction
import com.ericchee.songdataprovider.*
import com.syaphen.dotify.fragment.*
import com.syaphen.dotify.fragment.NowPlayingFragment.Companion.SONG_KEY
import com.syaphen.dotify.fragment.NowPlayingFragment.Companion.TAG
import kotlinx.android.synthetic.main.activity_master_main.*

class MasterMainActivity : AppCompatActivity(), OnSongClickListener {

    private var songPlaying: Song? = null

    companion object {
        const val CURR_SONG = "curr_song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_main)
        if (savedInstanceState != null){
            with(savedInstanceState) {
                songPlaying = getParcelable(CURR_SONG) // restore value for the current song
                songPlaying?.let { onSongClicked(it) } // refresh the miniPlayer listener
            }
        } else {
            val songListFragment = SongListFragment() // The view of the list of songs
            commitSongListFragment(songListFragment)
            initializeMiniPlayer(songListFragment)
        }
        // MiniPlayer & Back Button display
        supportFragmentManager.addOnBackStackChangedListener {
            if(supportFragmentManager.backStackEntryCount > 0) {
                miniPlayer.visibility = GONE
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                miniPlayer.visibility = VISIBLE
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
        if(supportFragmentManager.backStackEntryCount > 0) {
            miniPlayer.visibility = GONE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(TAG) as? NowPlayingFragment

    private fun commitSongListFragment(songListFragment: SongListFragment) {
        // Fetch song list and send to the fragment as an argument
        val argumentBundle = Bundle().apply {
            val listOfSongs: List<Song> = SongDataProvider.getAllSongs()
            putParcelableArrayList(SongListFragment.SONG_LIST, ArrayList(listOfSongs))
        }
        songListFragment.arguments = argumentBundle
        // commit the fragment
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()
    }

    private fun commitNowPlayingFragment() {
        val nullableNowPlayingFragment = getNowPlayingFragment()
        // Simply update the fragment if it's been created already
        if(nullableNowPlayingFragment != null) {
            songPlaying?.let { nullableNowPlayingFragment.updateSong(it) }
        } else {
            val nowPlayingFragment = NowPlayingFragment() // The view of the main music player
            // Fetch song list and send to the fragment as an argument
            val argumentBundle = Bundle().apply {
                putParcelable(SONG_KEY, songPlaying)
            }
            nowPlayingFragment.arguments = argumentBundle
            // commit the fragment
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, nowPlayingFragment)
                .addToBackStack("now playing")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }

    // set listener on the mini Player and the shuffle button
    private fun initializeMiniPlayer(songListFragment: SongListFragment) {
        miniPlayer.setOnClickListener {
            if(songPlaying != null) {
                miniPlayer.visibility = INVISIBLE
                commitNowPlayingFragment()
            }
        }
        // Shuffle feature
        shuffleButton.setOnClickListener {
            songListFragment.shuffleSongList()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(CURR_SONG, songPlaying)
    }

    override fun onSongClicked(songClicked: Song) {
        val displayedInfo = songClicked.title + " - " + songClicked.artist
        song_info_miniPlayer.text = displayedInfo
        songPlaying = songClicked
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }
}
