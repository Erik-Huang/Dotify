package com.syaphen.dotify.manager

import com.syaphen.dotify.model.Song
import kotlin.random.Random

class MusicManager {
    var listOfSongs: List<Song>
    var songPlaying: Song? = null
    var playCount = Random.nextInt(10, 200)

    init {
        listOfSongs = emptyList()
    }

    fun shuffle() {
        listOfSongs = listOfSongs.shuffled()
    }

    fun toggleSongPlaying(song: Song) {
        songPlaying = song
    }


}