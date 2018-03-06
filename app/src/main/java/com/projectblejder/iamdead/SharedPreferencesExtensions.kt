package com.projectblejder.iamdead

import android.content.SharedPreferences

fun SharedPreferences.inEdit(action: SharedPreferences.Editor.() -> Unit) {
    val editor = this.edit()
    editor.action()
    editor.apply()
}
