package com.yargisoft.yemekuygulamasi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yargisoft.yemekuygulamasi.R
import com.yargisoft.yemekuygulamasi.data.entity.SepetYemekler
import com.yargisoft.yemekuygulamasi.databinding.FragmentSepetBinding
import com.yargisoft.yemekuygulamasi.ui.adapter.SepetYemeklerAdapter
import com.yargisoft.yemekuygulamasi.ui.viewModel.SepetViewModel

class SepetFragment : Fragment() {

    private lateinit var binding: FragmentSepetBinding
    private lateinit var viewModel: SepetViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sepet, container, false)
        binding.sepetFragment = this
        binding.rvSepet.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        viewModel.sepetYemekListesi.observe(viewLifecycleOwner) {
            var tutar = 0
            it.forEach { sepetYemekler: SepetYemekler ->
                    tutar += sepetYemekler.yemek_fiyat * sepetYemekler.yemek_siparis_adet
                }
            val adapter = SepetYemeklerAdapter(requireContext(), it, viewModel)
            binding.sepetYemekAdapter = adapter
            binding.sepetTutari = "$tutar ₺"
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SepetViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        viewModel.sepettekiYemekleriYukle()
        super.onResume()
    }

    fun sepetiTemizle(it: View) {
        viewModel.sepetiTemizle(it)
        Toast.makeText(context,"Sepet Temizlendi", Toast.LENGTH_SHORT).show()
    }

    fun fabAnaSayfa(it: View) {
        Navigation.findNavController(it).navigate(R.id.sepetToAnaSayfa)
    }

}