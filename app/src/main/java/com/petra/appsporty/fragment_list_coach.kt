package com.petra.appsporty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var coachListAdapter: CoachListAdapter
    private lateinit var coachList: List<Coach>

    //data"coach
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
//        TambahData()
//        TampilkanData()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_coach, container, false)
        val rvCoachList: RecyclerView = view.findViewById(R.id.rvCoachList)
        val etFilterName: EditText = view.findViewById(R.id.etFilterName)
        val etFilterLocation: EditText = view.findViewById(R.id.etFilterLocation)
        val etFilterSportCategory: EditText = view.findViewById(R.id.etFilterSportCategory)
        val btnApplyFilters: ImageButton = view.findViewById(R.id.btnApplyFilters)



        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_list_coach.
         */
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

    private fun SiapkanData(){
        _nama = resources.getStringArray(R.array.namacoach).toMutableList()
        _foto = resources.getStringArray(R.array.fotocoach).toMutableList()
        _kategori = resources.getStringArray(R.array.kategoricoach).toMutableList()
        _lokasi = resources.getStringArray(R.array.lokasicoach).toMutableList()
        _umur = resources.getStringArray(R.array.umurcoach).toMutableList()
//        //knp eror ya anjg
//        _favorit = resources.getStringArray(R.array.favcoach).toMutableList()
//        _harga = resources.getStringArray(R.array.hargacoach).toMutableList()
//        _rating = resources.getStringArray(R.array.ratingcoach).toMutableList()
//
//        _trained = resources.getStringArray(R.array.trainedcoach).toMutableList()
//        _notes = resources.getStringArray(R.array.notescoach).toMutableList()
//        _lapangan = resources.getStringArray(R.array.lapangancoach).toMutableList()
//        _jam = resources.getStringArray(R.array.jamcoach).toMutableList()
//        _telp= resources.getStringArray(R.array.telpcoach).toMutableList()
//        _instagram= resources.getStringArray(R.array.instagramcoach).toMutableList()


    }
}