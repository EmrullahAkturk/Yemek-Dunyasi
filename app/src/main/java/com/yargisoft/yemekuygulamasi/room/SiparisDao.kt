package com.yargisoft.yemekuygulamasi.room

import androidx.room.*
import com.yargisoft.yemekuygulamasi.data.entity.Adresler
import com.yargisoft.yemekuygulamasi.data.entity.Kartlar
import com.yargisoft.yemekuygulamasi.data.entity.Siparisler


@Dao
interface SiparisDao {

    @Query("SELECT * FROM siparisler")
    suspend fun tumSiparisler(): List<Siparisler>

    @Insert
    suspend fun siparisEkle(siparis : Siparisler)

    @Insert
    suspend fun adresEkle(adres: Adresler)

    @Query("SELECT * FROM adresler")
    suspend fun tumAdresler(): List<Adresler>

    @Insert
    suspend fun kartEkle(kart: Kartlar)

    @Query("SELECT * FROM kartlar")
    suspend fun tumKartlar(): List<Kartlar>


}