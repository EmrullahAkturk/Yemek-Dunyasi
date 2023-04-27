package com.yargisoft.yemekuygulamasi.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "siparisler")

data class Siparisler(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "siparis_no")
    @NotNull var siparis_no: Int,

    @ColumnInfo(name = "isim_soyisim")
    @NotNull var isim_soyisim: String,

    @ColumnInfo(name = "telefon")
    @NotNull var telefon: String,

    @ColumnInfo(name = "adres")
    @NotNull var adres: String,

    @ColumnInfo(name = "urunler")
    @NotNull var urunler: String,

    @ColumnInfo(name = "kullanici_adi")
    @NotNull var kullanici_adi: String,


    )  {
}