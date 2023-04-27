package com.yargisoft.yemekuygulamasi.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SatinAlVMF(var application: Application):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SatinAlViewModel(application) as T
    }
}