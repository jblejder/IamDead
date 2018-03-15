package com.projectblejder.iamdead.presentation

import com.projectblejder.iamdead.infrastructure.JobDispatcher
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(
        private val jobDispatcher: JobDispatcher
) {

    val state = PublishSubject.create<MainActivityState>()

    fun setRefreshInterval(interval: Int) {
        state.onNext(MainActivityState.NewInterval(interval))
    }

    sealed class MainActivityState {
        data class Enabled(val get: Boolean) : MainActivityState()
        data class NewInterval(val value: Int) : MainActivityState()
    }
}



