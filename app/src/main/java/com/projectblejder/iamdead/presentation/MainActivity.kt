package com.projectblejder.iamdead.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.projectblejder.iamdead.R
import com.projectblejder.iamdead.databinding.ActivityMainBinding
import com.projectblejder.iamdead.presentation.base.Binding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    val binding = Binding<ActivityMainBinding>(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding.setContentView(this)
    }
}


