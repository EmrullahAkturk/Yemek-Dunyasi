package com.yargisoft.yemekuygulamasi.data.entity

data class SepetYemekCevap (var sepet_yemekler : List<SepetYemekler>, var success:Int) {
    /*fun yemekleriDondur():List<SepetYemekler>{
        val yeniListe = arrayListOf<SepetYemekler>()
        yeniListe.addAll(sepet_yemekler)
        return yeniListe
    }*/
}