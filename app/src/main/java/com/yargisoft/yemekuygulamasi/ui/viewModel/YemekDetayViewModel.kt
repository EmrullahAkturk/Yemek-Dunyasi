package com.yargisoft.yemekuygulamasi.ui.viewModel

import androidx.lifecycle.ViewModel
import com.yargisoft.yemekuygulamasi.data.repo.YemeklerDaoRepository

class YemekDetayViewModel :ViewModel() {
    val yrepo = YemeklerDaoRepository()

    fun sepeteEkle( yemek_adi: String,
                    yemek_resim_adi: String,
                    yemek_fiyat: Int,
                    yemek_siparis_adet: Int,
                    kullanici_adi: String,){
        yrepo.yemekSepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }


}