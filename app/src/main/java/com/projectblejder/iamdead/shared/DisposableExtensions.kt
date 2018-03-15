package com.projectblejder.iamdead.shared

import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

fun Disposable.disposeWith(disposable: DisposableContainer) {
    disposable.add(this)
}
