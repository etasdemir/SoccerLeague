package com.elacqua.soccerleague.utils

import android.net.Uri
import android.widget.ImageView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

object Helper {

    private lateinit var glideToVectorYou: GlideToVectorYou

    fun loadImage(url: String, imgView: ImageView) {
        val uri = Uri.parse(url)
        if (!this::glideToVectorYou.isInitialized) {
            glideToVectorYou = GlideToVectorYou.init()
        }
        glideToVectorYou.with(imgView.context).load(uri, imgView)
    }
}