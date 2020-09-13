package com.example.android.auriculoterapia_app

import android.app.Application
import com.cloudinary.android.MediaManager
import com.example.android.auriculoterapia_app.constants.ConfigCloudinary

class AuriculoterapiaTP : Application(){
    override fun onCreate() {
        super.onCreate()
        val config = ConfigCloudinary.config()
        MediaManager.init(applicationContext,config)
    }
}