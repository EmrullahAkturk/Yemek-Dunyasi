package com.yargisoft.yemekuygulamasi.data.repo

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.navigation.Navigation
import com.yargisoft.yemekuygulamasi.R
import com.yargisoft.yemekuygulamasi.data.entity.*
import com.yargisoft.yemekuygulamasi.retrofit.ApiUtils
import com.yargisoft.yemekuygulamasi.retrofit.YemeklerDao
import com.yargisoft.yemekuygulamasi.ui.viewModel.AnaSayfaViewModel
import com.yargisoft.yemekuygulamasi.ui.viewModel.YemekDetayViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class YemeklerDaoRepository {
    var yemeklerListesi: MutableLiveData<List<Yemekler>>
    var sepetYemekListesi: MutableLiveData<List<SepetYemekler>>
    var ydao: YemeklerDao

    init {
        ydao = ApiUtils.getYemeklerDao()
        yemeklerListesi = MutableLiveData()
        sepetYemekListesi = MutableLiveData()
    }

    fun yemekleriGoster(): MutableLiveData<List<Yemekler>> {
        return yemeklerListesi
    }

    fun sepetYemekleriGoster(): MutableLiveData<List<SepetYemekler>> {
        return sepetYemekListesi
    }

    fun yemekleriYukle() {
        ydao.tumYemekler().enqueue(object : Callback<YemeklerCevap> {
            override fun onResponse(call: Call<YemeklerCevap>, response: Response<YemeklerCevap>) {
                val liste = response.body()!!.yemekler
                yemeklerListesi.value = liste
            }

            override fun onFailure(call: Call<YemeklerCevap>, t: Throwable) {
            }
        })
    }

    fun yemekSepeteEkle(yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String,
    ) {
        if (sepetYemekListesi.value != null) {
            var checked: Boolean = false
            var ekliId = -1
            var ekliAdet = -1
            for (yemek in sepetYemekListesi.value!!) {
                if (yemek.yemek_adi == yemek_adi) {
                    checked = true
                    ekliId = yemek.sepet_yemek_id
                    ekliAdet = yemek.yemek_siparis_adet
                }
            }
            if (checked == false) {
                sepeteEkleKisa(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
            } else {
                sepettenYemekSil(ekliId,kullanici_adi)
                sepeteEkleKisa(yemek_adi,yemek_resim_adi,yemek_fiyat,ekliAdet+yemek_siparis_adet,kullanici_adi)

            }
        }
        else {
                sepeteEkleKisa(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        }
    }

    fun sepettenYemekSil(sepet_yemek_id: Int, kullanici_adi: String) {
        ydao.sepettenYemekSil(sepet_yemek_id, kullanici_adi).enqueue(object : Callback<CRUDCevap> {
            override fun onResponse(call: Call<CRUDCevap>, response: Response<CRUDCevap>) {
            }
            override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {
            }
        })
    }

    fun sepettekiYemekler() {
        ydao.sepettekiYemekleriGetir("emrullah").enqueue(object : Callback<SepetYemekCevap> {
            override fun onResponse(call: Call<SepetYemekCevap>,response: Response<SepetYemekCevap>) {
                val list = response.body()!!.sepet_yemekler
                sepetYemekListesi.value = list
            }
            override fun onFailure(call: Call<SepetYemekCevap>, t: Throwable) {
            }
        })
    }

    fun sepetiTemizle() {
        ydao.sepettekiYemekleriGetir("emrullah").enqueue(object : Callback<SepetYemekCevap> {
            override fun onResponse(call: Call<SepetYemekCevap>,response: Response<SepetYemekCevap>) {
                val list = response.body()!!.sepet_yemekler
                sepetYemekListesi.value = list
                for (eleman in list) {
                    sepettenYemekSil(eleman.sepet_yemek_id, eleman.kullanici_adi)
                }
                sepettekiYemekler()
            }
            override fun onFailure(call: Call<SepetYemekCevap>, t: Throwable) {
            }
        })

    }
    fun sepeteEkleKisa(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String,
    ) {
        ydao.sepeteYemekEkle(
            yemek_adi,
            yemek_resim_adi,
            yemek_fiyat,
            yemek_siparis_adet,
            kullanici_adi
        )
            .enqueue(object : Callback<CRUDCevap> {
                override fun onResponse(call: Call<CRUDCevap>, response: Response<CRUDCevap>) {
                    println(CRUDCevap(1, "hata").success)
                    sepettekiYemekler()
                }

                override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {
                    println(CRUDCevap(1, "hata").message)
                }
            })

    }

}