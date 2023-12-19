package com.petra.appsporty

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
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
import org.checkerframework.checker.index.qual.LengthOf
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

// notclicked : #560017AA
// clicked : #0017AA
enum class ButtonColor(val colorval: Int) {
    CLICKED(Color.parseColor(  "#0017AA")),
    NOTCLICKED(Color.parseColor( "#560017AA"))
}
class fragment_home : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_home().apply {
            }

        // simpan listOrder
        var listOrderUsers = arrayListOf<Order>()
        var listCoach = arrayListOf<Coach>()
    }


    // binding
    private lateinit var textViewDispDate: TextView
    lateinit var _rv_itemOrder: RecyclerView
    lateinit var _etSearch : EditText
    lateinit var _btnFilterAll: Button
    lateinit var _btnFilterOngoing: Button
    lateinit var _btnFilterCancel : Button
    lateinit var _btnFillerDone : Button
    lateinit var _tvSearchOrder : TextView

    // variabel dibutuhkan
    var filteredData : ArrayList<Order> = arrayListOf()
    var todayDate : String = ""
    var adapterP: OrderListAdapter = OrderListAdapter(listOrderUsers)


    // buat simpan button sekarang diklik
    lateinit var btnClicked : Button

    private suspend fun readDBOrders(): ArrayList<Order> {
        val db = Firebase.firestore

        // simpan data di arraylist
        var listOrder: ArrayList<Order> = arrayListOf()
        var activityNow = activity as MainActivity

        // looping di database
        val documents =
            db.collection("users").document(activityNow.getMyUsername()!!).collection("tbOrder")
                .get().await()

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        textViewDispDate = view.findViewById(R.id.tvDispTanggal)
        val today = Date()

        // Membuat objek SimpleDateFormat
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        // Memformat tanggal menjadi string
        val formattedDate = dateFormat.format(today)

        // set juga di todayDate
        todayDate = formattedDate

        // bind recyle view
        _rv_itemOrder = view.findViewById(R.id.rvActivity)
        _rv_itemOrder.layoutManager = LinearLayoutManager(context)
        _rv_itemOrder.adapter = adapterP

        adapterP.setOnItemClickCallback(object : OrderListAdapter.OnItemClickCallback {
            override fun onBtnDetailClicked(dataOrder: Order, dataCoach: Coach) {
                // pindah ke dalam activity selanjutnya
                var intent = Intent(requireContext(), DetailOrderPage::class.java)

                DetailOrderPage.dataOrder = dataOrder
                DetailOrderPage.dataCoach = dataCoach

                startActivity(intent)
            }
        })

        // Menampilkan tanggal di TextView
        textViewDispDate.text = "$formattedDate"

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("ONCREATEDVIEW", "MASUK WOIIIIIII")
        // mari kita bind
        _etSearch = view.findViewById(R.id.et_SearchOrder)
        _tvSearchOrder = view.findViewById(R.id.tv_iconSearch)
        _btnFilterAll = view.findViewById(R.id.btnFilterAll)
        _btnFilterOngoing = view.findViewById(R.id.btnFilterOngoing)
        _btnFillerDone = view.findViewById(R.id.btnFilterDone)
        _btnFilterCancel = view.findViewById(R.id.btnFilterCancel)

        // kasih tag
        _btnFilterAll.tag = 0
        _btnFilterOngoing.tag = 1
        _btnFillerDone.tag = 2
        _btnFilterCancel.tag = 3

        // masukan dalam list
        var listBtn : ArrayList<Button> = arrayListOf()
        listBtn.add(_btnFilterAll)
        listBtn.add(_btnFilterOngoing)
        listBtn.add(_btnFillerDone)
        listBtn.add(_btnFilterCancel)

        // default awal adalah all
        btnClicked = _btnFilterAll

        // baca database
        // didapat dari Database langsung dipassing ke dalam tampilkan data
        // ascyncronous get
        CoroutineScope(Dispatchers.IO).async {
            // coba ambil ini dulu
            listOrderUsers = readDBOrders()
            listCoach = getListCoachFromDatabase()

            // UPDATE STATUS TERBARU
            updateStatusAll(listOrderUsers)

            // masukin dalam filter , default adalah list semua data
            filteredData.addAll(listOrderUsers) // buat baru di variable filter

            withContext(Dispatchers.Main) {
                // ubah data
                adapterP.SetData(filteredData)
                adapterP.notifyDataSetChanged()
            }
        }

        // looping action btn
        for ((idx ,btn) in listBtn.withIndex()){
            btn.setOnClickListener {
                // kalo ga sama maka pindah warnanya
                if (btn != btnClicked) {
                    btnClicked.setBackgroundColor(ButtonColor.NOTCLICKED.colorval)
                    btn.setBackgroundColor(ButtonColor.CLICKED.colorval)

                    // ubah filter (0 = semua, 1 = ongoing, 2 = done, 3 = cancel)
                    // kalo semua berati tambahin aja dari listOrder
                    if (idx == 0){
                        // set data yang mau ditampilkan
                        filteredData = listOrderUsers
                        // filter nama juga
                        filteredData = ListOrderSortByNameCoach(filteredData, listCoach, _etSearch.text.toString())

                        adapterP.SetData(filteredData)
                    }
                    else{
                        // set yang difilter (di -1 karena status mulai dari 0-2 sedangkan yang di btn idx mulai dari 1-3
                        filteredData = ListOrderSortByStatus(listOrderUsers, (idx-1))
                        // filter nama juga
                        filteredData = ListOrderSortByNameCoach(filteredData, listCoach, _etSearch.text.toString())

                        adapterP.SetData(filteredData)
                    }

                    // setting btn diklik sekarang jadi variabel
                    btnClicked = btn
                }
            }
        }

        // action buat icon search
        _tvSearchOrder.setOnClickListener {
            Toast.makeText(context, "Search Clicked", Toast.LENGTH_SHORT)
            // cek btn sekarang apa
            // misal btn all
            if (btnClicked.tag.toString().toInt() == 0){
                // ga perlu cek status karena udah include semua
               filteredData = ListOrderSortByNameCoach(listOrderUsers, listCoach, _etSearch.text.toString())

            }
            else{
                // ga perlu cek status karena udah include semua
                filteredData = ListOrderSortByNameCoach(listOrderUsers, listCoach, _etSearch.text.toString())
                filteredData = ListOrderSortByStatus(filteredData, btnClicked.tag.toString().toInt())
            }

            adapterP.SetData(filteredData)
        }



    }

    override fun onResume() {
        super.onResume()

        Log.d("ONRESUME", "MASUK WOIIIIIII")
    }
}



// FUNC SENDIRI
fun getListCoachFromDatabase() : ArrayList<Coach> {
    val dbCoach = Firebase.firestore.collection("tbListCoach")
    var listDataCoach : ArrayList<Coach> = arrayListOf()
    dbCoach.get()
        .addOnSuccessListener { listCoachDB ->
            for (coachData in listCoachDB) {
                // simpan dulu sebagai coach
                var id = coachData.get("id").toString()
                var nama = coachData.get("name").toString()
                var umur = coachData.get("age").toString()
                var kategori = coachData.get("category").toString()
                var lapangan = coachData.get("facility").toString()
                var isfav = coachData.get("isFav").toString()
                var lokasi = coachData.get("location").toString()
                var catatan = coachData.get("notes").toString()
                var foto = coachData.get("photo").toString()
                var harga = coachData.get("price").toString()
                var rating = coachData.get("rating").toString()
                var telp = coachData.get("telp").toString()
                var time = coachData.get("time").toString()
                var trained = coachData.get("trained").toString()
                var ig = coachData.get("instagram").toString()

                // masukin sebagai class coach
                var getDataCoach = Coach(
                    id,
                    foto,
                    nama,
                    kategori,
                    lokasi,
                    umur,
                    harga,
                    isfav,
                    rating,
                    trained,
                    catatan,
                    telp,
                    ig,
                    lapangan,
                    time
                )

                listDataCoach.add(getDataCoach)
            }
        }
    Log.d("TAG COACH DATA", "HASIL COACH ${listDataCoach!!.size}")
    return listDataCoach
}

fun updateStatusAll(listDataOrder: ArrayList<Order>){
    // cek status dulu
    // misal dalam proses maka dicek apakah udah selesai
    for (order in listDataOrder){
        if (order.status.status == 0){
            // cek tanggal sekarang
            val today = Date()
            // Membuat objek SimpleDateFormat
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            // Memformat tanggal menjadi string
            val todayFormatted = dateFormat.format(today)

            // ubah format jadi list supaya gampang diambil per date month sama year (dd-mm-yyyy jadi [dd, mm, yyyy]
            val todayInList = todayFormatted.split("-")
            val orderDateList= order.tanggalDipesan.split("-")

            // cek misal tanggal sekarang udah lebih dari tanggal order maka status udah selesai
            if (todayInList[0].toInt() > orderDateList[0].toInt() && todayInList[1].toInt() >= orderDateList[1].toInt() && todayInList[2].toInt() >= orderDateList[2].toInt()){
                // set status sebagai selesai
                order.status = StatusOrder.DONE

                // update to database
                CoroutineScope(Dispatchers.IO).async {
                    // update ke database
                    val dbOrderUsers = Firebase.firestore.collection("users")
                        .document(order.username).collection("tbOrder")

                    withContext(Dispatchers.Main) {
                        // buat hashmapnya
                        val orderMap = hashMapOf(
                            "idOrder" to order.id,
                            "usernameOrder" to order.username,
                            "idCoachOrder" to order.idCoach,
                            "timeOrder" to order.jamDipesan,
                            "dateTrainingOrder" to order.tanggalDipesan,
                            "facilityOrder" to order.lapanganDipesan,
                            "dateOrder" to order.waktuPesan,
                            "priceOrder" to order.totalHarga,
                            "statusOrder" to order.status.status
                        )
                        dbOrderUsers.document(order.id).set(orderMap)
                        Log.d("JIR MANA WOEEEEE", "TEST MASUK")
                    }
                }
//                    adapterRV.notifyDataSetChanged()
            }
        }
    }
}

// buat sorting listorder pake status
fun ListOrderSortByStatus(listAllOrder : ArrayList<Order>, status:Int) : ArrayList<Order>{
    var listSorted : ArrayList<Order> = arrayListOf()

    for (order in listAllOrder){
        // cek misal sesuai yang diminta
        if (order.status.status == status){
            // misal sama maka masukin dalam list
            listSorted.add(order)
        }
    }

    return listSorted
}

fun ListOrderSortByNameCoach(listOrder : ArrayList<Order>, listCoach : ArrayList<Coach>, namaCoach:String) : ArrayList<Order> {
    var listSorted : ArrayList<Order> = arrayListOf()

    // misal ga filter apa2
    if (namaCoach.isNullOrEmpty()){
        // gausah filter langsung return
        return listOrder
    }

    // misal ada filter
    else{
        // cek filter
        for (order in listOrder){
            // misal mirip
            if ((namaCoach) in getCoachFromID(order.idCoach, listCoach)!!.name){
                // masukin ke dalam list
                listSorted.add(order)
            }
        }
        return listSorted
    }
}