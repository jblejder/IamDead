package com.projectblejder.iamdead.shared.dagger

import com.projectblejder.iamdead.presentation.MainActivity
import com.projectblejder.iamdead.shared.GlobalApplication
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ActivitiesModule::class
])
interface GlobalComponent : AndroidInjector<GlobalApplication>


@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}