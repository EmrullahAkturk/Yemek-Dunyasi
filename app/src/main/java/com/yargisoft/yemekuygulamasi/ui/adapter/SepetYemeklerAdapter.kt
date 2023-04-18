package com.yargisoft.yemekuygulamasi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yargisoft.yemekuygulamasi.data.entity.SepetYemekler
import com.yargisoft.yemekuygulamasi.databinding.CardTasarimYemekBinding
import com.yargisoft.yemekuygulamasi.databinding.SepetTasarimYemekBinding
import com.yargisoft.yemekuygulamasi.ui.fragment.AnaSayfaFragmentDirections
import com.yargisoft.yemekuygulamasi.ui.fragment.SepetFragmentDirections
import com.yargisoft.yemekuygulamasi.ui.fragment.SepetToDetayFragmentDirections
import com.yargisoft.yemekuygulamasi.ui.fragment.YemekDetayFragmentDirections
import com.yargisoft.yemekuygulamasi.ui.viewModel.SepetViewModel

class SepetYemeklerAdapter(
    var mContext: Context,
    var sepetYemekList: List<SepetYemekler>,
    var viewModel: SepetViewModel
) :
    RecyclerView.Adapter<SepetYemeklerAdapter.sepetTasarimHolder>() {

    inner class sepetTasarimHolder(tasarim: SepetTasarimYemekBinding) :
        RecyclerView.ViewHolder(tasarim.root) {
        var tasarim: SepetTasarimYemekBinding

        init {
            this.tasarim = tasarim
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): sepetTasarimHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val tasarim = SepetTasarimYemekBinding.inflate(layoutInflater, parent, false)
        return sepetTasarimHolder(tasarim)
    }

    override fun getItemCount(): Int {
        return sepetYemekList.size
    }

    override fun onBindViewHolder(holder: sepetTasarimHolder, position: Int) {
        val sepetYemek = sepetYemekList.get(position)
        val t = holder.tasarim
        t.sepetUrunAdet.text = sepetYemek.yemek_siparis_adet.toString()
        t.sepetUrunIsim.text = sepetYemek.yemek_adi
        t.sepetUrunFiyat.text = "${sepetYemek.yemek_fiyat.toString()}₺"
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${sepetYemek.yemek_resim_adi}"
        Glide.with(mContext).load(url).override(300, 300).into(t.imageViewSepet)

        t.btnDecreaseQuantity.setOnClickListener {
            var sepettekiAdet = sepetYemek.yemek_siparis_adet
            if (sepettekiAdet <= 1) {
                viewModel.sepettenYemekSil(sepetYemek.sepet_yemek_id, sepetYemek.kullanici_adi)
            } else {
                viewModel.sepettenYemekSil(sepetYemek.sepet_yemek_id, sepetYemek.kullanici_adi)
                viewModel.sepeteEkle(
                    sepetYemek.yemek_adi,
                    sepetYemek.yemek_resim_adi,
                    sepetYemek.yemek_fiyat,
                    -1,
                    sepetYemek.kullanici_adi
                )
            }

        }
        t.btnIncreaseQuantity.setOnClickListener {
            viewModel.sepettenYemekSil(sepetYemek.sepet_yemek_id, sepetYemek.kullanici_adi)
            viewModel.sepeteEkle(
                sepetYemek.yemek_adi,
                sepetYemek.yemek_resim_adi,
                sepetYemek.yemek_fiyat,
                1,
                sepetYemek.kullanici_adi
            )

        }
        t.urunCardView.setOnClickListener {
            val gecis = SepetFragmentDirections.sepetToDetay(sepetYemek = sepetYemek)
            Navigation.findNavController(it).navigate(gecis)
        }
    }



}