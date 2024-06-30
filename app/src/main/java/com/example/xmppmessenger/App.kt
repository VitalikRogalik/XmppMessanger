package com.example.xmppmessenger

import android.app.Application
import org.jivesoftware.smack.android.AndroidSmackInitializer

class DialogueApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidSmackInitializer.initialize(this)
    }
}
