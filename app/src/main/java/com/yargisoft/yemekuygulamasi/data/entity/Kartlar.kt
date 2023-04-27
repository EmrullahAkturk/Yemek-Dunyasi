package com.yargisoft.yemekuygulamasi.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "kartlar")

data class Kartlar(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "kart_id")
    @NotNull var kart_id: Int,

    @ColumnInfo(name = "isim_soyisim")
    @NotNull var isim_soyisim: String,

    @ColumnInfo(name = "kart_no")
    @NotNull var kart_no: String,

    @ColumnInfo(name = "tarih")
    @NotNull var tarih: String,

    @ColumnInfo(name = "cvv")
    @NotNull var cvv: String,

    @ColumnInfo(name = "kart_kisa_ad")
    @NotNull var kart_kisa_ad: String,



    ) {


}