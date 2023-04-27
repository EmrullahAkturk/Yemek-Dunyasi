package com.yargisoft.yemekuygulamasi.ui.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yargisoft.yemekuygulamasi.R
import com.yargisoft.yemekuygulamasi.databinding.FragmentSepetToDetayBinding
import com.yargisoft.yemekuygulamasi.ui.viewModel.SepetToDetayViewModel

class SepetToDetayFragment : Fragment() {
    private lateinit var binding: FragmentSepetToDetayBinding
    private lateinit var viewModel: SepetToDetayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sepet_to_detay, container, false)
        binding.sepetToDetayFragment = this
        val bundle: SepetToDetayFragmentArgs  by navArgs()
        val gelenYemek = bundle.sepetYemek
        binding.yemekNesnesi = gelenYemek
        binding.yemekFiyat = "${gelenYemek.yemek_fiyat}₺"
        binding.toolbarTitle = "Yemek Dünyası"
        binding.urunAdet = "1"
        try {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}"
            Glide.with(this).load(url).override(300, 300).into(binding.sepetDetayImageView)
        }catch (e:java.lang.Exception){
            println("${e.message}")
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : SepetToDetayViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        viewModel.sepetiYukle()
        super.onResume()
    }

    fun fabSepetTikla(it:View){
        Navigation.findNavController(it).navigate(R.id.sepetDetayToSepet)
    }

    fun fabAnaSayfaTikla(it:View){
        Navigation.findNavController(it).navigate(R.id.sepetDetayToMain)
    }

    fun adetArttir(urunAdet:String){
        binding.urunAdet = (urunAdet.toInt() + 1).toString()
    }
    fun adetAzalt(urunAdet:String){
        if (urunAdet.toInt()>1){
            binding.urunAdet = (urunAdet.toInt() - 1).toString()
        }
    }

    fun sepeteEkle(yemek_adi: String,
                   yemek_resim_adi: String,
                   yemek_fiyat: Int,
                   yemek_siparis_adet: String,
                   kullanici_adi: String){
        val yeniAdet = yemek_siparis_adet.toInt()
        viewModel.sepeteEkle(yemek_adi , yemek_resim_adi , yemek_fiyat , yeniAdet , kullanici_adi )
        Toast.makeText(context,"$yemek_adi sepete eklendi", Toast.LENGTH_SHORT).show()
        btnHandler()
    }

    fun btnHandler(){
        //Butona ard arda tıklanmasını önlemek iççin 1 dakikalık gecikme ekledik
        binding.btnDetaySepeteEkle.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - YemekDetayFragment.lastClickTime >= 500) {
                YemekDetayFragment.lastClickTime = currentTime
            } else {
                binding.btnDetaySepeteEkle.isEnabled = false
                Handler().postDelayed({
                    binding.btnDetaySepeteEkle.isEnabled = true
                }, 500)
            }
        }
    }
}