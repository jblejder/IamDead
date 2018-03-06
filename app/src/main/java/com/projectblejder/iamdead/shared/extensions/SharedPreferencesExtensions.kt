package com.projectblejder.iamdead.shared.extensions

import android.content.SharedPreferences

fun SharedPreferences.inEdit(action: SharedPreferences.Editor.() -> Unit) {
    val editor = this.edit()
    editor.action()
    editor.apply()
}
