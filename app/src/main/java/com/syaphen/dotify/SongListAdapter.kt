package com.syaphen.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import com.syaphen.dotify.R

class SongListAdapter(private val listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)

        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song= listOfSongs[position]
        holder.bind(song)
    }

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val songTitle = itemView.findViewById<TextView>(R.id.song_title)
        private val songArtist = itemView.findViewById<TextView>(R.id.song_artist)
        private val albumCover = itemView.findViewById<ImageView>(R.id.album_cover)

        fun bind(song: Song) {
            songTitle.text = song.title
            songArtist.text = song.artist
            albumCover.setImageResource(song.smallImageID)
        }
    }

}