package com.yargisoft.yemekuygulamasi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yargisoft.yemekuygulamasi.data.entity.SepetYemekler
import com.yargisoft.yemekuygulamasi.databinding.SepetTasarimYemekBinding
import com.yargisoft.yemekuygulamasi.ui.fragment.SepetFragmentDirections
import com.yargisoft.yemekuygulamasi.ui.viewModel.SepetViewModel

class SepetYemeklerAdapter(
    private var mContext: Context,
    private var sepetYemekList: List<SepetYemekler>,
    var viewModel: SepetViewModel
) :
    RecyclerView.Adapter<SepetYemeklerAdapter.SepetTasarimHolder>() {

    inner class SepetTasarimHolder(tasarim: SepetTasarimYemekBinding) :
        RecyclerView.ViewHolder(tasarim.root) {
        var tasarim: SepetTasarimYemekBinding

        init {
            this.tasarim = tasarim
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SepetTasarimHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val tasarim = SepetTasarimYemekBinding.inflate(layoutInflater, parent, false)
        return SepetTasarimHolder(tasarim)
    }

    override fun getItemCount(): Int {
        return sepetYemekList.size
    }

    override fun onBindViewHolder(holder: SepetTasarimHolder, position: Int) {
        val sepetYemek = sepetYemekList.get(position)
        val t = holder.tasarim
        t.sepetUrunSiparisAdet = "${sepetYemek.yemek_siparis_adet}"
        t.sepetCardUrunAd = sepetYemek.yemek_adi
        t.sepetCardUrunFiyat = "${sepetYemek.yemek_fiyat}₺"

        try {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${sepetYemek.yemek_resim_adi}"
            Glide.with(mContext).load(url).override(300, 300).into(t.imageViewSepet)
        }catch (e:Exception){
            println(e.message)
        }

        t.btnDecreaseQuantity.setOnClickListener {
            val sepettekiAdet = sepetYemek.yemek_siparis_adet
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