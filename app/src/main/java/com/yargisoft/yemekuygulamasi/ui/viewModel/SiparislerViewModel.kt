package com.yargisoft.yemekuygulamasi.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yargisoft.yemekuygulamasi.data.entity.SepetYemekler
import com.yargisoft.yemekuygulamasi.data.entity.Siparisler
import com.yargisoft.yemekuygulamasi.data.repo.SiparisDaoRepo
import com.yargisoft.yemekuygulamasi.data.repo.YemeklerDaoRepository

class SiparislerViewModel(application: Application) : AndroidViewModel(application) {
    private val sRepo = SiparisDaoRepo(application)
    val siparisList : MutableLiveData<List<Siparisler>>

    init {
        siparisList = sRepo.siparisleriGetir()
    }

    fun siparisleriGetir(){
        sRepo.siparisleriYukle()
    }
}