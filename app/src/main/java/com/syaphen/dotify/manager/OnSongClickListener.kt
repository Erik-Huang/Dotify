package com.syaphen.dotify.manager

import com.syaphen.dotify.model.Song

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}