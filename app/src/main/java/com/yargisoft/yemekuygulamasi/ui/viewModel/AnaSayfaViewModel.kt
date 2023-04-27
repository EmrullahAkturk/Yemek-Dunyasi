package com.yargisoft.yemekuygulamasi.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yargisoft.yemekuygulamasi.data.entity.Yemekler
import com.yargisoft.yemekuygulamasi.data.repo.YemeklerDaoRepository

class AnaSayfaViewModel: ViewModel() {

    private val yemekRepo = YemeklerDaoRepository()
    val yemeklerListesi : MutableLiveData<List<Yemekler>>

    init {
        yemekleriYukle()
        yemeklerListesi = yemekRepo.yemekleriGoster()
    }
    fun yemekleriYukle(){
        yemekRepo.yemekleriYukle()
    }
    fun urunAra(aramaKelimesi: String) {
        yemekRepo.anaSayfaUrunAra(aramaKelimesi)

    }

}