package com.petra.appsporty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_home.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var textViewDispDate: TextView
    lateinit var _rv_itemOrder : RecyclerView

    // simpan listOrder
    var listOrderUsers = arrayListOf<Order>()

    var adapterP : OrderListAdapter = OrderListAdapter(listOrderUsers)



    private suspend fun readDBOrders(): ArrayList<Order> {
        val db = Firebase.firestore
        // simpan data di arraylist
        var listOrder : ArrayList<Order> = arrayListOf()
        var activityNow = activity as MainActivity

        // looping di database
        val documents = db.collection("users").document(activityNow.getMyUsername()!!).collection("tbOrder").get().await()
        // looping di it nya
        for (i in documents) {
            // simpan per var nya

            var id = i.data.get("idOrder").toString()
            var idCoach = i.data.get("idCoachOrder").toString()
            var lapanganDipesan = i.data.get("facilityOrder").toString()
            var tanggalPesanan = i.data.get("dateOrder").toString()
            var tanggalDipesan = i.data.get("dateTrainingOrder").toString()
            var totalHarga = i.data.get("priceOrder").toString().toInt()
            var statusOrder = i.data.get("statusOrder").toString().toInt()
            var waktuDiPesan = i.data.get("timeOrder").toString()
            var username = i.data.get("usernameOrder").toString()

            // masukin ke dalam list
            listOrder.add(
                Order(
                    id,
                    username,
                    idCoach,
                    waktuDiPesan,
                    tanggalDipesan,
                    lapanganDipesan,
                    tanggalPesanan,
                    totalHarga,
                    getStatus(statusOrder)
                )
            )

            Log.d("MASUK LIST", "$lapanganDipesan, --> $totalHarga")
        }
        return listOrder
    }

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
        val view = inflater.inflate(R.layout.fragment_home ,container, false)

        textViewDispDate = view.findViewById(R.id.tvDispTanggal)
        val today = Date()

        // Membuat objek SimpleDateFormat
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        // Memformat tanggal menjadi string
        val formattedDate = dateFormat.format(today)

        // bind recyle view
        _rv_itemOrder = view.findViewById(R.id.rvActivity)
        _rv_itemOrder.layoutManager = LinearLayoutManager(context)
        _rv_itemOrder.adapter = adapterP


        // baca database
        // didapat dari Database langsung dipassing ke dalam tampilkan data
        // ascyncronous get
        lifecycleScope.launch(Dispatchers.IO) {
            // coba ambil ini dulu
            listOrderUsers = readDBOrders()

            // kalo udah baru set data
            withContext(Dispatchers.Main) {
                adapterP.SetData(listOrderUsers)
            }
        }

        // Menampilkan tanggal di TextView
        textViewDispDate.text = "$formattedDate"

        return view
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}