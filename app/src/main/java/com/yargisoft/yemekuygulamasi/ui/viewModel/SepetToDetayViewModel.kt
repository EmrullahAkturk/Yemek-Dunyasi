package com.yargisoft.yemekuygulamasi.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yargisoft.yemekuygulamasi.data.entity.SepetYemekler
import com.yargisoft.yemekuygulamasi.data.repo.YemeklerDaoRepository

class SepetToDetayViewModel: ViewModel() {
    private val ydao = YemeklerDaoRepository()
    private val sepetYemekListesi : MutableLiveData<List<SepetYemekler>>

    init {
        sepetiYukle()
        sepetYemekListesi = ydao.sepetYemekleriGoster()
    }

    fun sepeteEkle( yemek_adi: String,
                    yemek_resim_adi: String,
                    yemek_fiyat: Int,
                    yemek_siparis_adet: Int,
                    kullanici_adi: String,){
        ydao.yemekSepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }
   fun sepetiYukle(){
        ydao.sepettekiYemekler()
    }
}