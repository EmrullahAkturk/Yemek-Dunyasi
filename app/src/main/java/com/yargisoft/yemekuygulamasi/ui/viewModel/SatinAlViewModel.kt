package com.yargisoft.yemekuygulamasi.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yargisoft.yemekuygulamasi.data.entity.Adresler
import com.yargisoft.yemekuygulamasi.data.entity.Kartlar
import com.yargisoft.yemekuygulamasi.data.entity.SepetYemekler
import com.yargisoft.yemekuygulamasi.data.repo.SiparisDaoRepo
import com.yargisoft.yemekuygulamasi.data.repo.YemeklerDaoRepository

class SatinAlViewModel(application: Application) : AndroidViewModel(application) {

    val sRepo = SiparisDaoRepo(application)
    val yRepo = YemeklerDaoRepository()
    val kartListesi : MutableLiveData<List<Kartlar>>
    val adresListresi : MutableLiveData<List<Adresler>>

    init {
        kartListesi = sRepo.kartlariGetir()
        adresListresi= sRepo.adresleriGetir()
    }

    fun siparisOlustur(
        isim_soyisim: String,
        telefon: String,
        adres: String,
        urunler: String,
        kullanici_adi: String,
        tutar:String,
        siparisTarih:String
    ) {
        sRepo.siparisKaydet(isim_soyisim, telefon, adres, urunler, kullanici_adi,tutar,siparisTarih)
    }

    fun kartKaydet(isim: String,kartNo: String, tarih: String, cvv:String ,kisaAd: String) {
        sRepo.kartKaydet( isim, kartNo, tarih, cvv,kisaAd)
    }

    fun adresKaydet(adres: String ,isim_soyisim: String,telefon: String, adresKisa: String ) {
        sRepo.adresKaydet(adres, isim_soyisim, telefon, adresKisa)
    }

    fun kartlariYukle() {
        sRepo.kartlariYukle()
    }

    fun adresleriYukle() {
        sRepo.adresleriYukle()
    }
    fun siparisleriYukle(){
        sRepo.siparisleriYukle()
    }
    fun sepetiTemizle(){
        yRepo.sepetiTemizle()
    }


}