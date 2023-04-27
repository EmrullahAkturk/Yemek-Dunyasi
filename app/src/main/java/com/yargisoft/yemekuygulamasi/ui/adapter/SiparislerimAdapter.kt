package com.yargisoft.yemekuygulamasi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yargisoft.yemekuygulamasi.data.entity.Siparisler
import com.yargisoft.yemekuygulamasi.databinding.CardTasarimSiparislerimBinding
import com.yargisoft.yemekuygulamasi.databinding.CardTasarimYemekBinding
import com.yargisoft.yemekuygulamasi.databinding.SepetTasarimYemekBinding
import com.yargisoft.yemekuygulamasi.ui.viewModel.SiparislerViewModel

class SiparislerimAdapter(
    private var context: Context,
    private var siparisList: List<Siparisler>,
    var viewModel: SiparislerViewModel
) : RecyclerView.Adapter<SiparislerimAdapter.SiparislerimCardHolder>() {
    inner class SiparislerimCardHolder(tasarim: CardTasarimSiparislerimBinding) :
        RecyclerView.ViewHolder(tasarim.root) {
        var tasarim: CardTasarimSiparislerimBinding
        init {
            this.tasarim = tasarim
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiparislerimCardHolder {
        val layoutInflater = LayoutInflater.from(context)
        val tasarim = CardTasarimSiparislerimBinding.inflate(layoutInflater, parent, false)
        return SiparislerimCardHolder(tasarim)
    }

    override fun getItemCount(): Int {
        return siparisList.size
    }

    override fun onBindViewHolder(holder: SiparislerimCardHolder, position: Int) {
        val siparis = siparisList.get(position)
        val t = holder.tasarim
        println("siparislerim: ${siparisList}")

        t.siparisFiyat.text = "${siparis.tutar}₺"
        t.siparisTarih.text = siparis.siparisTarih
        t.siparisNo.text = "Siparis No: ${siparis.siparis_no.toString()}"
        try {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${siparis.urunler}.png"
            Glide.with(context).load(url).override(150, 180).into(t.siparisGorsel)
        }catch (e:Exception){
            println(e.message)
        }
    }
}