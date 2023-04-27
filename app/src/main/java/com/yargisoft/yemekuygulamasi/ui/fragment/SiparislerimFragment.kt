package com.yargisoft.yemekuygulamasi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yargisoft.yemekuygulamasi.R
import com.yargisoft.yemekuygulamasi.databinding.FragmentSepetBinding
import com.yargisoft.yemekuygulamasi.databinding.FragmentSiparislerimBinding
import com.yargisoft.yemekuygulamasi.ui.adapter.SepetYemeklerAdapter
import com.yargisoft.yemekuygulamasi.ui.adapter.SiparislerimAdapter
import com.yargisoft.yemekuygulamasi.ui.viewModel.SepetViewModel
import com.yargisoft.yemekuygulamasi.ui.viewModel.SiparislerViewModel


class SiparislerimFragment : Fragment() {

    private lateinit var binding: FragmentSiparislerimBinding
    private lateinit var viewModel: SiparislerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_siparislerim, container, false)
        // Inflate the layout for this fragment
        binding.rvSiparis.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        viewModel.siparisList.observe(viewLifecycleOwner){
            val adapter = SiparislerimAdapter(requireContext(), it, viewModel)
            binding.siparislerimAdapter = adapter
        }


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SiparislerViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        viewModel.siparisleriGetir()
        super.onResume()
    }


}