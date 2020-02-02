package com.mohsenoid.gifbrowser.util.extension

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import org.koin.core.module.Module

fun Activity.hideKeyboard() {
    this.currentFocus?.let { view ->
        val inputMethodManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

operator fun Module.plus(modules: List<Module>): List<Module> = listOf(this) + modules
