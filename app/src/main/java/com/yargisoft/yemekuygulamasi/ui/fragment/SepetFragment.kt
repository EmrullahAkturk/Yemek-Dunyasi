package com.yargisoft.yemekuygulamasi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yargisoft.yemekuygulamasi.R
import com.yargisoft.yemekuygulamasi.databinding.FragmentSepetBinding
import com.yargisoft.yemekuygulamasi.ui.adapter.SepetYemeklerAdapter
import com.yargisoft.yemekuygulamasi.ui.adapter.YemeklerAdapter
import com.yargisoft.yemekuygulamasi.ui.viewModel.SepetViewModel

class SepetFragment : Fragment() {

    private lateinit var binding:FragmentSepetBinding
    private lateinit var viewModel: SepetViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sepet, container, false)
        binding.sepetFragment = this
        binding.rvSepet.layoutManager =StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        viewModel.sepetYemekListesi.observe(viewLifecycleOwner) {
           val adapter = SepetYemeklerAdapter(requireContext(), it, viewModel)
            binding.sepetYemekAdapter = adapter
        }

        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : SepetViewModel by viewModels()
        viewModel = tempViewModel
    }
    fun sepetiTemizle(it:View){
        viewModel.sepetiTemizle(it)
    }

    override fun onResume() {
        super.onResume()
        viewModel.sepettekiYemekleriYukle()
    }

}