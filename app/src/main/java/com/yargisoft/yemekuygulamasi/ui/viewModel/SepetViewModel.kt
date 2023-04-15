package com.yargisoft.yemekuygulamasi.ui.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.yargisoft.yemekuygulamasi.data.entity.SepetYemekler
import com.yargisoft.yemekuygulamasi.data.entity.Yemekler
import com.yargisoft.yemekuygulamasi.data.repo.YemeklerDaoRepository

class SepetViewModel:ViewModel() {
    val ydao = YemeklerDaoRepository()
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

    fun sepetiTemizle(it:View){
        Snackbar.make(it,"Tüm sepet silinsin mi?", Snackbar.LENGTH_LONG)
            .setAction("EVET"){
                ydao.sepetiTemizle()
            }.show()


    }

}