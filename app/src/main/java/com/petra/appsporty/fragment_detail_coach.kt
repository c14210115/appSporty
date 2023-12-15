package com.petra.appsporty

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
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
    private var db = FirebaseFirestore.getInstance()
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
        //val db = FirebaseFirestore.getInstance()
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
        var tvTrained = view.findViewById<TextView>(R.id.tvTrained)
        var btnBackToList = view.findViewById<ImageButton>(R.id.imageButton)

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
        val trained = arguments?.getString("trained")


        val instagramUsername = arguments?.getString("ig")
        tvInstagram.setOnClickListener {
            // Open Instagram profile in a web browser
            openInstagramProfile(instagramUsername)
        }
        val whatsapp = arguments?.getString("wa")
        tvWA.setOnClickListener {
            // Open Instagram profile in a web browser
            openWhatsApp(whatsapp)
        }
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
        tvTrained.text = trained

//        btnOrder.setOnClickListener {
//            // Buat instance fragment pemesanan coach
//            val orderCoachFragment = fragment_pemesanan_coach.newInstance("data1", "data2")
//
//            // Melakukan transaksi untuk pindah ke fragment pemesanan coach
//            val transaction = requireActivity().supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragmentContainerView, orderCoachFragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }
        btnOrder.setOnClickListener {
            getNextOrderId { orderId ->

                val orderDetails = hashMapOf(
                    "id" to orderId,
                    "nama" to nama,
                    "cabor" to cabor,
                    "lokasi" to lokasi,
                )
                //add database
                db.collection("coachOrders").document(orderId)
                    .set(orderDetails)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(
                            requireContext(),
                            //cek database order id
                            "Order placed successfully with ID: $orderId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Error placing order", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error adding document", e)
                    }
            }
        }
        btnBackToList.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }


        return view
    }
    private fun getNextOrderId(onComplete: (String) -> Unit) {
        val countersRef = db.collection("counters").document("orderCounter")
        //cb ngitung id transaksi
        countersRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    countersRef.update("value", FieldValue.increment(1))
                        .addOnSuccessListener {
                            countersRef.get()
                                .addOnSuccessListener { updatedDocumentSnapshot ->
                                    val orderId = updatedDocumentSnapshot.getLong("value")
                                    onComplete(orderId.toString())
                                }
                                .addOnFailureListener { e ->
                                    Log.e(TAG, "Error getting updated order counter", e)
                                }
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Error incrementing order counter", e)
                        }
                } else {

                    //cek dokumen ada apa nda
                    countersRef.set(hashMapOf("value" to 1))
                        .addOnSuccessListener {
                            onComplete("1")
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Error setting initial order counter", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error checking order counter existence", e)
            }
    }
    private fun openInstagramProfile(username: String?) {
        if (!username.isNullOrEmpty()) {
            val instagramUri = Uri.parse("http:"+"//www.instagram.com/"+username)
            val intent = Intent(Intent.ACTION_VIEW, instagramUri)
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error opening Instagram", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "No browser installed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Instagram username not available", Toast.LENGTH_SHORT).show()
        }
    }
    private fun openWhatsApp(phoneNumber: String?) {
        if (!phoneNumber.isNullOrEmpty()) {
            val whatsappUri = Uri.parse("http://api.whatsapp.com/send?phone=$phoneNumber")
            val intent = Intent(Intent.ACTION_VIEW, whatsappUri)
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error opening WhatsApp", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "No WhatsApp app installed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "WhatsApp phone number not available", Toast.LENGTH_SHORT).show()
        }
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