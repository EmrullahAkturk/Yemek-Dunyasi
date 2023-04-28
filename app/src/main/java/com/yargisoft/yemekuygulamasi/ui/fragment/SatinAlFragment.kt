package com.yargisoft.yemekuygulamasi.ui.fragment

import android.app.AlertDialog
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.yargisoft.yemekuygulamasi.R
import com.yargisoft.yemekuygulamasi.data.entity.Kartlar
import com.yargisoft.yemekuygulamasi.databinding.FragmentSatinAlBinding
import com.yargisoft.yemekuygulamasi.ui.viewModel.SatinAlVMF
import com.yargisoft.yemekuygulamasi.ui.viewModel.SatinAlViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SatinAlFragment : Fragment() {
    private lateinit var binding: FragmentSatinAlBinding
    private lateinit var viewModel: SatinAlViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_satin_al, container, false)

        var kisa_adres = ""
        var kart_kisa = ""

        binding.saveAdress.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Adresiniz için kısa isim giriniz")
                val input = EditText(requireContext())
                builder.setView(input)
                builder.setPositiveButton("Tamam") { dialog, which ->
                    kisa_adres = input.text.toString()
                    // kullanıcının girdiği metin burada kullanılabilir
                }
                builder.setNegativeButton("İptal") { dialog, which ->
                    dialog.cancel()
                    binding.saveAdress.isChecked = false
                }
                builder.show()
            }
        }
        binding.saveCard.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Kartınız için kısa isim giriniz")
                val input = EditText(requireContext())
                builder.setView(input)
                builder.setPositiveButton("Tamam") { dialog, which ->
                    kart_kisa = input.text.toString()
                    // kullanıcının girdiği metin burada kullanılabilir
                }
                builder.setNegativeButton("İptal") { dialog, which ->
                    dialog.cancel()
                    binding.saveCard.isChecked = false
                }
                builder.show()
            }
        }
        binding.btnOdeme.setOnClickListener {
            if (binding.saveAdress.isChecked) {
                viewModel.adresKaydet(
                    binding.adres.text.toString(),
                    binding.adSoyad.text.toString(),
                    binding.telefon.text.toString(),
                    kisa_adres
                )
            }
            if (binding.saveCard.isChecked) {
                viewModel.kartKaydet(
                    binding.cardOwner.text.toString(),
                    binding.cardNo.text.toString(),
                    binding.cardDate.text.toString(),
                    binding.cardCvv.text.toString(),
                    kart_kisa
                )
            }
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val current = LocalDateTime.now().format(formatter)

            var urunGorsel = arrayListOf<String>()
            resources.getStringArray(R.array.yemekler).forEach { urun -> urunGorsel.add(urun) }

            viewModel.siparisOlustur(
                binding.adSoyad.text.toString(),
                binding.telefon.text.toString(),
                binding.adres.text.toString(),
                urunGorsel.get((0..8).random()).toString(),
                "emrullah",
                (0..500).random().toString(),
                current
            )
            viewModel.sepetiTemizle()
            Navigation.findNavController(it).navigate(R.id.siparisOlusturulduGecis)
        }

        val kartlar = arrayListOf<String>()
        viewModel.kartListesi.observe(viewLifecycleOwner) {
            if (viewModel.kartListesi.value != null) {
                for (kart in viewModel.kartListesi.value!!) {
                    kartlar.add(kart.kart_kisa_ad)
                }
            }
        }
        val adresler = arrayListOf<String>()
        viewModel.adresListresi.observe(viewLifecycleOwner) {
            if (viewModel.kartListesi.value != null) {
                for (adres in viewModel.adresListresi.value!!) {
                    adresler.add(adres.adres_kisa_ad)
                }
            }
        }

        binding.spinner1.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, kartlar
        )
        binding.spinner2.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, adresler
        )

        binding.fabSepetSatinAl.setOnClickListener{
            fabSepetTikla(it)
        }
        binding.fabAnaSayfaSatinAl.setOnClickListener{
            fabAnaSayfaTikla(it)
        }


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SatinAlViewModel by viewModels() {
            SatinAlVMF(requireActivity().application)
        }
        viewModel = tempViewModel
    }


    override fun onResume() {
        super.onResume()
        viewModel.siparisleriYukle()
        viewModel.kartlariYukle()
        viewModel.adresleriYukle()
    }

    fun fabSepetTikla(it:View){
        println("tikladin")
        Navigation.findNavController(it).navigate(R.id.satinAlToSepet)
    }
    fun fabAnaSayfaTikla(it:View){
        Navigation.findNavController(it).navigate(R.id.satinAlToAnaSayfa)
    }


}