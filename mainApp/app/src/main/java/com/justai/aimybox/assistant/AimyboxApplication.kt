package com.justai.aimybox.assistant

import android.app.Application
import android.content.Context
import com.justai.aimybox.Aimybox
import com.justai.aimybox.api.aimybox.AimyboxDialogApi
import com.justai.aimybox.components.AimyboxProvider
import com.justai.aimybox.core.Config
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToText
import com.justai.aimybox.speechkit.google.platform.GooglePlatformTextToSpeech
import java.util.*

open class AimyboxApplication : Application(), AimyboxProvider {

    override val aimybox by lazy { createAimybox(this) }

    open fun createAimybox(context: Context): Aimybox {
        val unitId = UUID.randomUUID().toString();

        val textToSpeech = GooglePlatformTextToSpeech(context, Locale.ENGLISH)
        val speechToText = GooglePlatformSpeechToText(context, Locale.ENGLISH)

        val dialogApi = AimyboxDialogApi("", unitId, "https://bot.jaicp.com/chatapi/webhook/zenbox/aWoYYOJg:94e0dd2d8fcab0357bf8e08fb84e07b542ee75bc")

        return Aimybox(Config.create(speechToText, textToSpeech, dialogApi))
    }


//    override val aimybox: Aimybox
//        get() = createAimybox(this)
}
