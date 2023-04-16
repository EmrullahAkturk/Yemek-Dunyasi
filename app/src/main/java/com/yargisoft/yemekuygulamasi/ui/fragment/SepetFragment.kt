package com.yargisoft.yemekuygulamasi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
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
            if(viewModel.sepetYemekListesi.value!!.size ==  0) {
                binding.urunYazisi = "Sepetinizde ürün yok"
            }else{
                val adapter = SepetYemeklerAdapter(requireContext(), it, viewModel)
                binding.sepetYemekAdapter = adapter
            }
        }

        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : SepetViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.sepettekiYemekleriYukle()
    }
    fun sepetiTemizle(it:View){
        viewModel.sepetiTemizle(it)
    }
    fun fabAnaSayfa(it:View){
        Navigation.findNavController(it).navigate(R.id.sepetToAnaSayfa)
    }

}