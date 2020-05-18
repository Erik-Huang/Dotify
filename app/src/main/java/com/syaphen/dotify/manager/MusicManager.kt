package com.syaphen.dotify.manager

import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class MusicManager {
    var listOfSongs: List<Song> = SongDataProvider.getAllSongs()
    var songPlaying: Song? = null
    var playCount = 0

    fun shuffle() {
        listOfSongs = listOfSongs.shuffled()
    }

    fun toggleSongPlaying(song: Song) {
        songPlaying = song
    }


}