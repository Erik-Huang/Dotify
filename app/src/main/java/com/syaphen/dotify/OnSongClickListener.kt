package com.syaphen.dotify

import com.ericchee.songdataprovider.Song

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}