package com.petra.appsporty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_list_coach.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_list_coach : Fragment() {
    companion object {
        private var coachList = arrayListOf<Coach>()
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_list_coach().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var coachListAdapter: CoachListAdapter
    private var filteredList : ArrayList<Coach> = ArrayList()
    private lateinit var adapterP: CoachListAdapter

    val dbUser = Firebase.firestore

//    private var btnSearchFilter = findViewById<Button>(R.id.btnApplyFilters)
//    private var inputFilter = view.findViewById<EditText>(R.id.etFilterName)


    //data"coach
    private var _id: MutableList<String> = emptyList<String>().toMutableList()
    private var _nama: MutableList<String> = emptyList<String>().toMutableList()
    private var _foto: MutableList<String> = emptyList<String>().toMutableList()
    private var _kategori: MutableList<String> = emptyList<String>().toMutableList()
    private var _lokasi: MutableList<String> = emptyList<String>().toMutableList()
    private var _harga: MutableList<String> = emptyList<String>().toMutableList()
    private var _umur: MutableList<String> = emptyList<String>().toMutableList()
    private var _rating: MutableList<String> = emptyList<String>().toMutableList()
    private var _trained: MutableList<String> = emptyList<String>().toMutableList()
    private var _lapangan: MutableList<String> = emptyList<String>().toMutableList()
    private var _jam: MutableList<String> = emptyList<String>().toMutableList()
    private var _notes: MutableList<String> = emptyList<String>().toMutableList()
    private var _telp: MutableList<String> = emptyList<String>().toMutableList()
    private var _instagram: MutableList<String> = emptyList<String>().toMutableList()
    private var _favorit: MutableList<String> = emptyList<String>().toMutableList()


    private lateinit var _rvCoach: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _rvCoach = view.findViewById(R.id.rvCoachList)
        SiapkanData()
        TambahData()
        TampilkanData()
        val etFilterName: EditText = view.findViewById(R.id.etFilterName)
        val btnApplyFilters: ImageButton = view.findViewById(R.id.btnApplyFilters)

        btnApplyFilters.setOnClickListener {
            adapterP = CoachListAdapter(filteredList)
            _rvCoach.adapter = adapterP
            val categoryFilter = etFilterName.text.toString()
            adapterP.filterByCategory(categoryFilter)
            filteredList.clear()
            filteredList.addAll(coachList.filter { it.category.equals(categoryFilter, ignoreCase = true) })
            adapterP.notifyDataSetChanged()
        }
        var btnDetail = view.findViewById<Button>(R.id.btnInfo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_coach, container, false)
        val rvCoachList: RecyclerView = view.findViewById(R.id.rvCoachList)

        return view
    }

    private fun SiapkanData(){
        _id = resources.getStringArray(R.array.idcoach).toMutableList()
        _nama = resources.getStringArray(R.array.namacoach).toMutableList()
        _foto = resources.getStringArray(R.array.fotocoach).toMutableList()
        _kategori = resources.getStringArray(R.array.kategoricoach).toMutableList()
        _lokasi = resources.getStringArray(R.array.lokasicoach).toMutableList()
        _umur = resources.getStringArray(R.array.umurcoach).toMutableList()

        _favorit = resources.getStringArray(R.array.favcoach).toMutableList()
        _harga = resources.getStringArray(R.array.hargacoach).toMutableList()
        _rating = resources.getStringArray(R.array.ratingcoach).toMutableList()

        _trained = resources.getStringArray(R.array.trainedcoach).toMutableList()
        _notes = resources.getStringArray(R.array.notescoach).toMutableList()
        _lapangan = resources.getStringArray(R.array.lapangancoach).toMutableList()
        _jam = resources.getStringArray(R.array.jamcoach).toMutableList()
        _telp= resources.getStringArray(R.array.telpcoach).toMutableList()
        _instagram= resources.getStringArray(R.array.instagramcoach).toMutableList()
    }


    private fun TambahData() {
        coachList.clear()
        for (position in _nama.indices) {
            val data = Coach(
                _id[position],
                _foto[position],
                _nama[position],
                _kategori[position],
                _lokasi[position],
                _umur[position],
                _harga[position],
                _favorit[position],
                _rating[position],
                _trained[position],
                _notes[position],
                _telp[position],
                _instagram[position],
                _lapangan[position],
                _jam[position]
            )
            coachList.add(data)
//masukan firebase untuk data coach
//            val coachM = hashMapOf(
//                "id" to data.id,
//                "photo" to  data.photo,
//                "name" to data.name,
//                "category" to data.category,
//                "location" to data.location,
//                "age" to data.age,
//                "price" to data.price,
//                "isFav" to "True",
//                "rating" to  data.rating,
//                "trained" to data.trained,
//                "notes" to data.notes,
//                "telp" to data.telp,
//                "instagram" to data.instagram,
//                "facility" to data.facility,
//                "time" to data.time,
//            )
//            dbUser.collection("tbListCoach").document(data.id).set(coachM)


        }
    }
    private fun TampilkanData() {
//        _rvPahlawan.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

            _rvCoach.layoutManager = LinearLayoutManager(requireContext())

            val adapterP = CoachListAdapter(coachList)
            _rvCoach.adapter = adapterP

            adapterP.setOnItemClickCallback(object : CoachListAdapter.OnItemClickCallback {

                override fun onItemClicked(pos: Int) {

                }

                override fun favorites(pos: Int, data: Coach) {
                    //ambil username yang login
                    var username: String? = null
                    if (activity is MainActivity) {
                        val mainActivity = (activity as MainActivity?)
                        username = mainActivity?.getMyUsername()
                        Log.d("username", "${username}")
                    }

                    //buat hasmap, dimana favoritenya jadi true
                    val coachMap = hashMapOf(
                        "id" to data.id,
                        "photo" to  data.photo,
                        "name" to data.name,
                        "category" to data.category,
                        "location" to data.location,
                        "age" to data.age,
                        "price" to data.price,
                        "isFav" to "True",
                        "rating" to  data.rating,
                        "trained" to data.trained,
                        "notes" to data.notes,
                        "telp" to data.telp,
                        "instagram" to data.instagram,
                        "facility" to data.facility,
                        "time" to data.time,
                    )
                    dbUser.collection("users").document(username.toString())
                        .collection("tbFavorites").document(data.id).set(coachMap)


                    Toast.makeText(
                            requireContext(),
                    "Added to Favorites",
                    Toast.LENGTH_SHORT).show()
                }

                override fun delData(pos: Int) {
                    TODO("Not yet implemented")
                }
            })


//        btnSearchFilter.setOnClickListener {
//            val categoryFilter = inputFilter.text.toString()
//            adapterP.filterByCategory(categoryFilter)
//        }

    }
}