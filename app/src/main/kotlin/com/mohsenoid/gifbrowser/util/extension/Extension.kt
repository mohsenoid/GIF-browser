package com.mohsenoid.gifbrowser.util.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import org.koin.core.module.Module

fun Activity.hideKeyboard() {
    this.currentFocus?.let { view ->
        val inputMethodManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun EditText.onSearchAction(callback: (View) -> Unit) {
    setOnEditorActionListener { view, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            callback.invoke(view)
            true
        } else {
            false
        }
    }
}
