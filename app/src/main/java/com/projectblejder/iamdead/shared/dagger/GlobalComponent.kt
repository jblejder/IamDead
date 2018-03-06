package com.projectblejder.iamdead.shared.dagger

import android.content.Context
import com.projectblejder.iamdead.presentation.MainActivity
import com.projectblejder.iamdead.shared.GlobalApplication
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    GlobalBinds::class,
    ActivitiesModule::class
])
interface GlobalComponent : AndroidInjector<GlobalApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GlobalApplication>()
}

@Module
interface GlobalBinds {
    @Binds
    fun bind(globalApplication: GlobalApplication): Context
}

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}