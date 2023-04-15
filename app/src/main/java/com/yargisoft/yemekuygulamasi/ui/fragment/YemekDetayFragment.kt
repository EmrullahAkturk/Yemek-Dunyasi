package com.yargisoft.yemekuygulamasi.ui.fragment

import android.app.Activity
import android.os.Bundle
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
        binding.yemekDetayFiyat.text = gelenYemek.yemek_fiyat.toString()

        try {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${bundle.yemek.yemek_resim_adi}"
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

    fun sepeteEkle(yemek_adi: String,
                        yemek_resim_adi: String,
                        yemek_fiyat: Int,
                        yemek_siparis_adet: Int,
                        kullanici_adi: String){
        viewModel.sepeteEkle(yemek_adi , yemek_resim_adi , yemek_fiyat , yemek_siparis_adet , kullanici_adi )
    }

    override fun onResume() {
        super.onResume()
        viewModel.sepetiYukle()
    }

}
