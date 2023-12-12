package com.petra.appsporty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_detail_coach.newInstance] factory method to
 * create an instance of this fragment.
 */

//Anton buat ini ya gaes hehehe
class fragment_detail_coach : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_coach, container, false)

        var imageDetail = view.findViewById<ImageView>(R.id.imgCoachDetail)
        var tvNama = view.findViewById<TextView>(R.id.tvNama)
        var tvCabor = view.findViewById<TextView>(R.id.tvCabor)
        var tvLokasi = view.findViewById<TextView>(R.id.tvLokasi)
        var tvUmur = view.findViewById<TextView>(R.id.tvLokasi)
        var tvInstagram = view.findViewById<TextView>(R.id.tvIg)
        var tvWA = view.findViewById<TextView>(R.id.tvWA)
        var btnOrder = view.findViewById<Button>(R.id.btnOrder)
        var tvCoachPrice = view.findViewById<TextView>(R.id.tvCoachprice)
        var tvRate = view.findViewById<TextView>(R.id.tvRateDetail)
        var tvNotes = view.findViewById<TextView>(R.id.tvDispNotes)

        val nama = arguments?.getString("nama")
        val gambar = arguments?.getString("gambar")
        val harga = arguments?.getString("harga")
        val cabor = arguments?.getString("cabor")
        val umur = arguments?.getString("umur")
        val rate = arguments?.getString("rate")
        val note = arguments?.getString("note")
        val ig = arguments?.getString("ig")
        val wa = arguments?.getString("wa")
        val lokasi = arguments?.getString("lokasi")

        //membaca image
        val imageRes = context?.resources?.getIdentifier(
            gambar, "drawable", context?.packageName
        )
        imageDetail.setImageResource(imageRes!!)
        imageDetail.scaleType
        tvNama.text = nama
        tvCabor.text = cabor
        tvLokasi.text = lokasi
        tvUmur.text = umur
        tvInstagram.text = ig
        tvWA.text = wa
        tvCoachPrice.text = harga
        tvRate.text = rate
        tvNotes.text = note

        btnOrder.setOnClickListener {
            // Buat instance fragment pemesanan coach
            val orderCoachFragment = fragment_pemesanan_coach.newInstance("data1", "data2")

            // Melakukan transaksi untuk pindah ke fragment pemesanan coach
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, orderCoachFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_detail_coach.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_detail_coach().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}