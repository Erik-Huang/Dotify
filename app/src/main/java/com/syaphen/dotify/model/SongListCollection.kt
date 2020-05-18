package com.syaphen.dotify.model

data class SongListCollection(
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>
)