package com.yargisoft.yemekuygulamasi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import com.yargisoft.yemekuygulamasi.R
import com.yargisoft.yemekuygulamasi.databinding.FragmentSiparisOlusturulduBinding
import com.yargisoft.yemekuygulamasi.ui.viewModel.SiparislerViewModel

class SiparisOlusturulduFragment : Fragment() {

    private lateinit var binding: FragmentSiparisOlusturulduBinding
    private lateinit var viewModel: SiparislerViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_siparis_olusturuldu,
            container,
            false
        )

        val animationView: LottieAnimationView = binding.animationView
        animationView.setAnimation(R.raw.receipt)
        animationView.playAnimation()

        viewModel.siparisleriGetir()
        viewModel.siparisList.observe(viewLifecycleOwner) {
            binding.textView2.text = "Sipariş Numaranız : 000000${it.last().siparis_no} "

        }

        binding.fabSepetOlusturuldu.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.olusturulduToSepet)
        }
        binding.fabAnaSayfaOlusturuldu.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.olusturulduToAnaSayfa)
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SiparislerViewModel by viewModels()
        viewModel = tempViewModel
    }
}