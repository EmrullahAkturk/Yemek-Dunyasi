package com.yargisoft.yemekuygulamasi.retrofit

import com.yargisoft.yemekuygulamasi.data.entity.CRUDCevap
import com.yargisoft.yemekuygulamasi.data.entity.SepetYemekCevap
import com.yargisoft.yemekuygulamasi.data.entity.YemeklerCevap
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface YemeklerDao {

    @GET("tumYemekleriGetir.php")
    fun tumYemekler(): Call<YemeklerCevap>

    @POST("sepeteYemekEkle.php")
    @FormUrlEncoded
    fun sepeteYemekEkle(
        @Field("yemek_adi") yemek_adi: String,
        @Field("yemek_resim_adi") yemek_resim_adi: String,
        @Field("yemek_fiyat") yemek_fiyat: Int,
        @Field("yemek_siparis_adet") yemek_siparis_adet: Int,
        @Field("kullanici_adi") kullanici_adi: String
    ): Call<CRUDCevap>


    @POST("sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun sepettekiYemekleriGetir(
       @Field("kullanici_adi") kullanici_adi: String
    ): Call<SepetYemekCevap>


    @POST("sepettenYemekSil.php")
    @FormUrlEncoded
    fun sepettenYemekSil(
        @Field("sepet_yemek_id") sepet_yemek_id:Int,
        @Field("kullanici_adi") kullanici_adi: String
    ): Call<CRUDCevap>



}