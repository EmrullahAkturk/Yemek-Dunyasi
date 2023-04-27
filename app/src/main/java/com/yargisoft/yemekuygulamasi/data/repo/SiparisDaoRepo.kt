package com.yargisoft.yemekuygulamasi.data.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yargisoft.yemekuygulamasi.data.entity.Adresler
import com.yargisoft.yemekuygulamasi.data.entity.Kartlar
import com.yargisoft.yemekuygulamasi.data.entity.Siparisler
import com.yargisoft.yemekuygulamasi.room.Veritabani
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SiparisDaoRepo(application: Application) {
    var siparisListesi: MutableLiveData<List<Siparisler>>
    var adresListesi: MutableLiveData<List<Adresler>>
    var kartListesi: MutableLiveData<List<Kartlar>>
    var vt: Veritabani

    init {
        kartListesi = MutableLiveData()
        adresListesi = MutableLiveData()
        siparisListesi = MutableLiveData()
        vt = Veritabani.veritabaniErisim(application)!!
    }

    fun siparisleriGetir(): MutableLiveData<List<Siparisler>> {
        return siparisListesi
    }

    fun adresleriGetir(): MutableLiveData<List<Adresler>> {
        return adresListesi
    }

    fun kartlariGetir(): MutableLiveData<List<Kartlar>> {
        return kartListesi
    }

    fun siparisKaydet(
        isim_soyisim: String,
        telefon: String,
        adres: String,
        urunler: String,
        kullanici_adi: String
    ) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val yeniSiparis = Siparisler(0, isim_soyisim, telefon, adres, urunler, kullanici_adi)
            println("siparis list: ${siparisListesi.value}")
            vt.siparisDao().siparisEkle(yeniSiparis)
        }
    }


    fun adresKaydet(adres: String ,isim_soyisim: String,telefon: String, adresKisa: String ,  ) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val yeniAdres = Adresler(0,  adres, isim_soyisim,telefon, adresKisa )
            println("adres list: ${adresListesi.value}")
            vt.siparisDao().adresEkle(yeniAdres)
        }
    }

    fun adresleriYukle() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            adresListesi.value = vt.siparisDao().tumAdresler()
        }
    }



    fun kartKaydet(isim: String,kartNo: String, tarih: String, cvv:String ,kisaAd: String) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val yeniKart = Kartlar(0, isim, kartNo,tarih , cvv,kisaAd)
            println("Kart list: ${kartListesi.value}")
            vt.siparisDao().kartEkle(yeniKart)
        }
    }

    fun kartlariYukle() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            kartListesi.value = vt.siparisDao().tumKartlar()
        }
    }

     fun siparisleriYukle() {
         val job = CoroutineScope(Dispatchers.Main).launch {
             siparisListesi.value = vt.siparisDao().tumSiparisler()
         }
     }
}