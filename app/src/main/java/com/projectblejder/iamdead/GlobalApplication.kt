package com.projectblejder.iamdead

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class GlobalApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerGlobalComponent.create()
    }
}
