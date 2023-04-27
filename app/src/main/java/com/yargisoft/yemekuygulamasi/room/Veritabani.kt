package com.yargisoft.yemekuygulamasi.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yargisoft.yemekuygulamasi.data.entity.Adresler
import com.yargisoft.yemekuygulamasi.data.entity.Kartlar
import com.yargisoft.yemekuygulamasi.data.entity.Siparisler


@Database(entities = [Siparisler::class,Kartlar::class,Adresler::class], version = 1)
abstract class Veritabani : RoomDatabase() {
    abstract fun siparisDao(): SiparisDao

    companion object {
        //static
        var INSTANCE: Veritabani? = null

        fun veritabaniErisim(context: Context): Veritabani? {
            if (INSTANCE == null) {
                synchronized(Veritabani::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        Veritabani::class.java,
                        "siparis.sqlite").createFromAsset("siparis.sqlite").build()
                }
            }
            return INSTANCE
        }

    }

}