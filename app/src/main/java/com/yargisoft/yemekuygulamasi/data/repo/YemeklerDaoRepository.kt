package com.yargisoft.yemekuygulamasi.data.repo

import androidx.lifecycle.MutableLiveData
import com.yargisoft.yemekuygulamasi.data.entity.*
import com.yargisoft.yemekuygulamasi.retrofit.ApiUtils
import com.yargisoft.yemekuygulamasi.retrofit.YemeklerDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YemeklerDaoRepository {
    var yemeklerListesi: MutableLiveData<List<Yemekler>>
    var sepetYemekListesi: MutableLiveData<List<SepetYemekler>>
    val list2 : List<SepetYemekler>
        get() {
           return emptyList()
        }
   private var ydao: YemeklerDao

    init {
        ydao = ApiUtils.getYemeklerDao()
        yemeklerListesi = MutableLiveData()
        sepetYemekListesi = MutableLiveData()
    }

    fun anaSayfaUrunAra(aramaKelimesi:String){
        println("Çalışıyorum")
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
                println("Hata: Menü yükle")

            }
        })
    }

    fun yemekSepeteEkle(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String,
    ) {
        sepettekiYemekler()
        if (sepetYemekListesi.value != null) {
            var checked = false
            var ekliId = -1
            var ekliAdet = -1
            for (yemek in sepetYemekListesi.value!!) {
                if (yemek.yemek_adi == yemek_adi) {
                    checked = true
                    ekliId = yemek.sepet_yemek_id
                    ekliAdet = yemek.yemek_siparis_adet
                }
            }
            if (!checked) {
                sepeteEkleKisa(
                    yemek_adi,
                    yemek_resim_adi,
                    yemek_fiyat,
                    yemek_siparis_adet,
                    kullanici_adi
                )
            } else {
                sepettenYemekSil(ekliId, kullanici_adi)
                sepeteEkleKisa(
                    yemek_adi,
                    yemek_resim_adi,
                    yemek_fiyat,
                    ekliAdet + yemek_siparis_adet,
                    kullanici_adi
                )
            }
        } else {
            sepeteEkleKisa(
                yemek_adi,
                yemek_resim_adi,
                yemek_fiyat,
                yemek_siparis_adet,
                kullanici_adi
            )
        }
    }

    fun sepettenYemekSil(sepet_yemek_id: Int, kullanici_adi: String) {
        ydao.sepettenYemekSil(sepet_yemek_id, kullanici_adi).enqueue(object : Callback<CRUDCevap> {
            override fun onResponse(call: Call<CRUDCevap>, response: Response<CRUDCevap>) {
                sepettekiYemekler()
            }
            override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {
                println("Hata: Sepetteki yemekten sil")
            }
        })
    }


    fun sepettekiYemekler() {
        ydao.sepettekiYemekleriGetir("emrullah").enqueue(object : Callback<SepetYemekCevap> {
            override fun onResponse(
                call: Call<SepetYemekCevap>,
                response: Response<SepetYemekCevap>
            ) {
                val list = response.body()!!.sepet_yemekler
                sepetYemekListesi.value = list

            }
            override fun onFailure(call: Call<SepetYemekCevap>, t: Throwable) {
                println("Hata: Sepetteki yemekler")
                /*Response olarak SepetCevap nesnesinden bir instance bekliyoruz. Fakat sepetimizde ürün olmadığında
                 web servis response olarak bir liste değil, 1 değerini dönderiyor.
                 Bu sebeple veritabanı halihazırda boş ise veya son eleman silinip veritabanı boşaltılırsa
                 bir liste dönmediği için onFailure metodu çalışmakta. Bundan dolayı da sepetimizdeki son elemanı silmek
                  istediğimizde response metodu çalışmadığı için listemizin value değeri güncellenmemekte
                  ve observer tetiklenmemektedir. Bu nedenle sepetimizin boş görünebilmesi için sepetimize boş bir liste atıyoruz*/
                sepetYemekListesi.value = list2

            }
        })
    }

    fun sepetiTemizle() {
        ydao.sepettekiYemekleriGetir("emrullah").enqueue(object : Callback<SepetYemekCevap> {
            override fun onResponse(
                call: Call<SepetYemekCevap>,
                response: Response<SepetYemekCevap>
            ) {
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

    private fun sepeteEkleKisa(
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
                    sepettekiYemekler()
                }

                override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {
                   println("Hata: sepete ekle")
                }
            })

    }

}