package com.yargisoft.yemekuygulamasi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yargisoft.yemekuygulamasi.data.entity.Yemekler
import com.yargisoft.yemekuygulamasi.databinding.CardTasarimYemekBinding
import com.yargisoft.yemekuygulamasi.ui.fragment.AnaSayfaFragmentDirections
import com.yargisoft.yemekuygulamasi.ui.viewModel.AnaSayfaViewModel


class YemeklerAdapter(
    private var mContext: Context,
    private var yemeklerListesi: List<Yemekler>,
    var viewModel: AnaSayfaViewModel
) :
    RecyclerView.Adapter<YemeklerAdapter.CardTasarimTutucu>() {

    inner class CardTasarimTutucu(tasarim: CardTasarimYemekBinding) :
        RecyclerView.ViewHolder(tasarim.root) {
        var tasarim: CardTasarimYemekBinding

        init {
            this.tasarim = tasarim
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val layoutInflater = LayoutInflater.from(mContext)
        val tasarim = CardTasarimYemekBinding.inflate(layoutInflater, parent, false)

        return CardTasarimTutucu(tasarim)
    }

    override fun getItemCount(): Int {
        return yemeklerListesi.size
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val yemek = yemeklerListesi.get(position)
        val t = holder.tasarim
        t.cardUrunAd = yemek.yemek_adi
        t.cardUrunFiyat = "${yemek.yemek_fiyat}₺"

        try {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
            Glide.with(mContext).load(url).override(300, 300).into(t.yemekResim)
        } catch (e: Exception) {
            println(e.message)
        }
        t.cardAnaSayfa.setOnClickListener {
            val gecis = AnaSayfaFragmentDirections.yemekDetayGecis(yemek = yemek)
            Navigation.findNavController(it).navigate(gecis)
        }
    }

}