package com.dassol.kanolio.di

import com.dassol.kanolio.presentation.view_model.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainActivityViewModel(get(), get()) }
}