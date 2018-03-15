package com.projectblejder.iamdead.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jakewharton.rxbinding2.widget.userChanges
import com.projectblejder.iamdead.R
import com.projectblejder.iamdead.databinding.ActivityMainBinding
import com.projectblejder.iamdead.presentation.MainActivityViewModel.MainActivityState.Enabled
import com.projectblejder.iamdead.presentation.MainActivityViewModel.MainActivityState.NewInterval
import com.projectblejder.iamdead.presentation.base.Binding
import com.projectblejder.iamdead.shared.disposeWith
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    val binding = Binding<ActivityMainBinding>(R.layout.activity_main)
    val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding.setContentView(this)

        binding.get.intervalSeek.userChanges()
                .doOnNext { Log.d("seek", "raw: $it") }
                .map { 5 + (it * (60 - 5) / 100) }
                .doOnNext { Log.d("seek", "domain: $it") }
                .subscribe { viewModel.setRefreshInterval(it) }
                .disposeWith(disposeBag)

        binding.get.enableSwitch.setOnClickListener {

        }

        viewModel.state
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when (it) {
                        is Enabled -> reduceCheckbox(it)
                        is NewInterval -> reduceInterval(it)
                    }
                }
    }

    private fun reduceInterval(it: NewInterval) {
        binding.get.intervalEditText.setText(it.value.toString())
        val back = ((it.value - 5).toFloat() * 100f / (60f - 5f))
        Log.d("seek", "back: $back")
        binding.get.intervalSeek.progress = round(back).toInt()
    }

    private fun reduceCheckbox(enabled: Enabled) {
        val binding = binding.get
        binding.enableSwitch.isChecked = enabled.get
    }
}
