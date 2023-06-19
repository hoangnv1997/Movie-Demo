package com.hoangnv97.moviedemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MovieDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                private var stackTraceElement: StackTraceElement? = null
                override fun createStackElementTag(element: StackTraceElement): String {
                    stackTraceElement = element
                    return "MovieApp:${super.createStackElementTag(element)}#${element.methodName}"
                }

                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    val caller = stackTraceElement?.let { "(${it.fileName}:${it.lineNumber}" } ?: ""
                    super.log(priority, tag, "$message$caller)", t)
                }
            })
        }
    }
}
