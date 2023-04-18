package com.yargisoft.yemekuygulamasi.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yargisoft.yemekuygulamasi.R
import com.yargisoft.yemekuygulamasi.databinding.FragmentAnaSayfaBinding
import com.yargisoft.yemekuygulamasi.ui.adapter.YemeklerAdapter
import com.yargisoft.yemekuygulamasi.ui.viewModel.AnaSayfaViewModel

class AnaSayfaFragment : Fragment(){

    private lateinit var binding: FragmentAnaSayfaBinding
    private lateinit var viewModel: AnaSayfaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ana_sayfa, container, false)
        binding.anaSayfaFragment = this
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        viewModel.yemeklerListesi.observe(viewLifecycleOwner) {
            val adapter = YemeklerAdapter(requireContext(), it, viewModel)
            binding.yemeklerAdapter = adapter
        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: AnaSayfaViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.yemekleriYukle()
    }

    fun fabTikla(it:View){
        Navigation.findNavController(it).navigate(R.id.sepetDetayGecis)
    }
}