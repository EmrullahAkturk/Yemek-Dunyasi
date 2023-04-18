package com.yargisoft.yemekuygulamasi.ui.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.yargisoft.yemekuygulamasi.data.entity.SepetYemekler
import com.yargisoft.yemekuygulamasi.data.repo.YemeklerDaoRepository

class SepetViewModel:ViewModel() {
    private val ydao = YemeklerDaoRepository()
    val sepetYemekListesi : MutableLiveData<List<SepetYemekler>>
    init {
        sepettekiYemekleriYukle()
        sepetYemekListesi = ydao.sepetYemekleriGoster()
    }

    fun sepettekiYemekleriYukle(){
        ydao.sepettekiYemekler()
    }

    fun sepettenYemekSil(sepet_yemek_id:Int,kullanici_adi:String){
        ydao.sepettenYemekSil(sepet_yemek_id,kullanici_adi)
    }

    fun sepeteEkle( yemek_adi: String,
                    yemek_resim_adi: String,
                    yemek_fiyat: Int,
                    yemek_siparis_adet: Int,
                    kullanici_adi: String,){
        ydao.yemekSepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }

    fun sepetiTemizle(it:View){
        Snackbar.make(it,"Tüm sepet silinsin mi?", Snackbar.LENGTH_LONG)
            .setAction("EVET"){
                ydao.sepetiTemizle()
            }.show()
    }

}