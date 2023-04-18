package com.yargisoft.yemekuygulamasi.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yargisoft.yemekuygulamasi.R
import com.yargisoft.yemekuygulamasi.databinding.FragmentYemekDetayBinding
import com.yargisoft.yemekuygulamasi.ui.viewModel.YemekDetayViewModel

class YemekDetayFragment : Fragment() {
    private lateinit var binding: FragmentYemekDetayBinding
    private lateinit var viewModel: YemekDetayViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_yemek_detay, container, false)
        binding.yemekDetayFragment = this
        val bundle: YemekDetayFragmentArgs by navArgs()
        val gelenYemek = bundle.yemek
        binding.yemekNesnesi = gelenYemek
        binding.yemekFiyat = "${gelenYemek.yemek_fiyat.toString()}₺"
        binding.toolbarTitle = "Yemek Dünyası"
        binding.urunAdet = "1"

        try {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}"
            Glide.with(this).load(url).override(300, 300).into(binding.detayImageView)
        }catch (e:java.lang.Exception){
            println("${e.message}")
        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : YemekDetayViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        /* Detay sayfasına geldiğimizde sepette hangi ürünlerin
            var olduğunu bilmemiz için listemizi güncellememiz gerekiyor.
            Bu yüzden alttaki metodu çalıştırdık*/
        viewModel.sepetiYukle()
    }

    fun sepeteEkle(yemek_adi: String,
                        yemek_resim_adi: String,
                        yemek_fiyat: Int,
                        yemek_siparis_adet: String,
                        kullanici_adi: String){

        var yeniAdet = yemek_siparis_adet.toInt()
        viewModel.sepeteEkle(yemek_adi , yemek_resim_adi , yemek_fiyat , yeniAdet , kullanici_adi )
    }

    fun adetArttir(urunAdet:String){
        binding.urunAdet = (urunAdet.toInt() + 1).toString()
    }
    fun adetAzalt(urunAdet:String){
        if (urunAdet.toInt()>1){
            binding.urunAdet = (urunAdet.toInt() - 1).toString()
        }
    }
    fun fabSepetTikla(it:View){
        Navigation.findNavController(it).navigate(R.id.detayToSepet)
    }
    fun fabAnaSayfaTikla(it:View){
        Navigation.findNavController(it).navigate(R.id.detayToAnaSayfa)
    }
}
