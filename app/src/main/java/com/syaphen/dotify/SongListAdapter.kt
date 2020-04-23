package com.syaphen.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(initialListOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    private var listOfSongs: List<Song> = initialListOfSongs.toList() // duplicate the list
    var onSongClickListener: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)

        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song= listOfSongs[position]
        holder.bind(song)
    }

    fun change (newListOfSongs: List<Song>) {
        // Normal way up applying updates to list
        //listOfPeople = newPeople
        //notifyDataSetChanged()

        // Animated way of applying updates to list
        val callback = SongDiffCallback(listOfSongs, newListOfSongs)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)

        // We update the list
        listOfSongs = newListOfSongs


    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val songTitle = itemView.findViewById<TextView>(R.id.song_title)
        private val songArtist = itemView.findViewById<TextView>(R.id.song_artist)
        private val albumCover = itemView.findViewById<ImageView>(R.id.album_cover)

        fun bind(song: Song) {
            songTitle.text = song.title
            songArtist.text = song.artist
            albumCover.setImageResource(song.smallImageID)
            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
        }
    }

}