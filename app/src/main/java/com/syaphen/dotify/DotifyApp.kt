package com.syaphen.dotify

import android.app.Application
import com.syaphen.dotify.manager.MusicManager
import com.syaphen.dotify.manager.APIManager


class DotifyApp: Application() {

    lateinit var musicManager: MusicManager
    lateinit var apiManager: APIManager

    override fun onCreate() {
        super.onCreate()

        musicManager = MusicManager()

        apiManager = APIManager(this)
    }

}