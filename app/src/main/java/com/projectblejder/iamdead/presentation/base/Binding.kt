package com.projectblejder.iamdead.presentation.base

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding


class Binding<out T : ViewDataBinding>(
        private val layoutId: Int
) {

    private lateinit var bindings: T
    val get: T
        get() = bindings

    fun setContentView(activity: Activity) {
        bindings = DataBindingUtil.setContentView(activity, layoutId)
    }
}