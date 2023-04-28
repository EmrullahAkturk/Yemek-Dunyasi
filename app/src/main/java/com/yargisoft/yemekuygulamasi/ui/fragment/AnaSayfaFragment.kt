package com.yargisoft.yemekuygulamasi.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yargisoft.yemekuygulamasi.R
import com.yargisoft.yemekuygulamasi.databinding.FragmentAnaSayfaBinding
import com.yargisoft.yemekuygulamasi.ui.adapter.YemeklerAdapter
import com.yargisoft.yemekuygulamasi.ui.viewModel.AnaSayfaViewModel
import com.yargisoft.yemekuygulamasi.ui.viewModel.SepetViewModel

class AnaSayfaFragment : Fragment() {
    private lateinit var binding: FragmentAnaSayfaBinding
    private lateinit var viewModel: AnaSayfaViewModel
    private lateinit var viewModelSepet: SepetViewModel

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


        // Define Popup Menu
        val popupMenu = PopupMenu(requireContext(), binding.btnMenuAc)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        // Set click listener for Popup Menu items
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_3 -> {
                    Navigation.findNavController(requireActivity(),R.id.btnMenuAc).navigate(R.id.sepetDetayGecis)
                    true
                }
                R.id.menu_siparislerim -> {
                    // Handle popup item 2 click
                    Navigation.findNavController(requireActivity(),R.id.btnMenuAc).navigate(R.id.mainToSiparislerimGecis)
                    true
                }
                else -> false
            }
        }

        // Set click listener for Popup Button
       binding.btnMenuAc.setOnClickListener {
           try {
               val fieldMPopup =  PopupMenu::class.java.getDeclaredField("mPopup")
               fieldMPopup.isAccessible = true
               val mPopup = fieldMPopup.get(popupMenu)
               mPopup.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java).invoke(mPopup,true)
           }catch (e:Exception){
               Log.e("AnaSayfaFragment","Error while showing menu")
           }finally {
               popupMenu.show()
           }
        }

        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: AnaSayfaViewModel by viewModels()
        val tempViewModel2:SepetViewModel by viewModels()
        viewModelSepet = tempViewModel2
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.yemekleriYukle()
    }

    fun fabTikla(it: View) {
        Navigation.findNavController(it).navigate(R.id.sepetDetayGecis)
    }


}