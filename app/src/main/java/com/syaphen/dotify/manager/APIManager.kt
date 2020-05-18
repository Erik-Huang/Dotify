package com.syaphen.dotify.manager

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.syaphen.dotify.model.*

class APIManager(context: Context) {

    private val queue: RequestQueue = Volley.newRequestQueue(context)
    private val songLibraryURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/musiclibrary.json"

    fun fetchSongs(onSongsReady: (List<Song>) -> Unit, onError: (() -> Unit)? = null) {
        val request = StringRequest(Request.Method.GET, songLibraryURL,
            { response ->
                // Success
                val gson = Gson()
                val collection = gson.fromJson(response, SongListCollection::class.java )
                onSongsReady(collection.songs)
            }, {
                onError?.invoke()
            }
        )
        queue.add(request)
    }

    fun fetchArtists() {
        //TODO
    }

    fun fetchUserInfo() {
        //TODO
    }

}