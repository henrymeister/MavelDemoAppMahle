package com.henry.marvelmahle.di

import com.henry.marvelmahle.presentation.characters.CharactersMainVM
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

// This class has the declaration of the View Models
val viewModelModule = module {
    viewModel { CharactersMainVM(get(),get()) }
}