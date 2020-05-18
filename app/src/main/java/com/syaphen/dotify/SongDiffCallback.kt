package com.syaphen.dotify

import androidx.recyclerview.widget.DiffUtil
import com.syaphen.dotify.model.Song

class SongDiffCallback (
    private val oldSong: List<Song>,
    private val newSong: List<Song>
    ): DiffUtil.Callback() {


     override fun getOldListSize(): Int = oldSong.size

     override fun getNewListSize(): Int = newSong.size

     override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
         val oldTemp = oldSong[oldItemPosition]
         val newTemp= newSong[newItemPosition]
         return oldTemp.id == newTemp.id
     }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTemp = oldSong[oldItemPosition]
        val newTemp = newSong[newItemPosition]
        return oldTemp.title == newTemp.title
    }
}