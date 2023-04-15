package com.yargisoft.yemekuygulamasi.data.entity

import java.io.Serializable

class SepetYemekler(
    var sepet_yemek_id: Int,
    var yemek_adi: String,
    var yemek_resim_adi: String,
    var yemek_fiyat: Int,
    var yemek_siparis_adet: Int,
    var kullanici_adi: String
) : Serializable {

    override fun toString(): String {
        return "SepetYemekler(sepet_yemek_id=$sepet_yemek_id, yemek_adi='$yemek_adi',yemek_resim_adi='$yemek_resim_adi',yemek_fiyat='$yemek_fiyat',yemek_siparis_adet = '$yemek_siparis_adet',kullanici_adi='$kullanici_adi')"
    }


}