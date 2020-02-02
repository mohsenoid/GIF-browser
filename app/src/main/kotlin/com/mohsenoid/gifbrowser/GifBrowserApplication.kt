package com.mohsenoid.gifbrowser

import android.app.Application
import com.mohsenoid.gifbrowser.injection.appModule
import com.mohsenoid.gifbrowser.injection.dataModule
import com.mohsenoid.gifbrowser.injection.presentationModule
import com.mohsenoid.gifbrowser.injection.qualifier.QualifierName
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.HashMap

class GifBrowserApplication : Application(), KoinComponent {

    private val isDebug: Boolean = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()

        if (isDebug) setupTimber()

        startKoin {
            val appProperties: HashMap<String, Any> = hashMapOf(
                QualifierName.IS_DEBUG to isDebug,
                QualifierName.BASE_URL to BuildConfig.BASE_URL,
                QualifierName.API_KEY to BuildConfig.API_KEY
            )
            properties(appProperties)

            if (isDebug) androidLogger()

            androidContext(this@GifBrowserApplication)
            modules(appModule + dataModule + presentationModule)
        }
    }

    private fun setupTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                // adding file name and line number link to logs
                return "${super.createStackElementTag(element)}(${element.fileName}:${element.lineNumber})"
            }
        })
    }
}
