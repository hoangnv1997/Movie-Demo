package com.hoangnv97.moviedemo.common

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

fun Activity.hideKeyboard() {
    currentFocus?.let { view ->
        ContextCompat.getSystemService(applicationContext, InputMethodManager::class.java)
            ?.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}
