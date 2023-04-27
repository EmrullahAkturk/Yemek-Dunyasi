package com.yargisoft.yemekuygulamasi.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "adresler")

data class Adresler(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "adres_id") //Veritabanında kisi_id sütunununa denk geldiğini belirtiyoruz
    @NotNull var adres_id: Int,

    @ColumnInfo(name = "adres")
    @NotNull var adres: String,

    @ColumnInfo(name = "isim_soyisim")
    @NotNull var isim_soyisim: String,

    @ColumnInfo(name = "telefon")
    @NotNull var telefon: String,

    @ColumnInfo(name = "adres_kisa_ad")
    @NotNull var adres_kisa_ad: String,



) {

}