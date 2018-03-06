package com.projectblejder.iamdead.shared


import com.projectblejder.iamdead.shared.dagger.DaggerGlobalComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class GlobalApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerGlobalComponent.create()
    }
}
