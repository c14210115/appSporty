package com.petra.appsporty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
 * Use the [fragment_favorite.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_favorite : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var _rvFav: RecyclerView
    // untuk array dan firebase
    val dbFavorties = Firebase.firestore
    private var favList = arrayListOf<Coach>()

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
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _rvFav = view.findViewById(R.id.rvFavorite)

        getFavFromFirebase()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_favorite.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_favorite().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun getFavFromFirebase() {
        var username: String? = ""
        if (activity is MainActivity) {
            val mainActivity = (activity as MainActivity?)
            username = mainActivity?.getMyUsername()
            Log.d("username", "${username}")
        }

        dbFavorties.collection("users").document(username.toString()).collection("tbFavorites")
            .get().addOnSuccessListener { id ->
            for (document in id) {
                val data =
                    Coach(
                        document.data["id"].toString(),
                        document.data["photo"].toString(),
                        document.data["name"].toString(),
                        document.data["category"].toString(),
                        document.data["location"].toString(),
                        document.data["age"].toString(),
                        document.data["price"].toString(),
                        document.data["isFav"].toString(),
                        document.data["rating"].toString(),
                        document.data["trained"].toString(),
                        document.data["notes"].toString(),
                        document.data["telp"].toString(),
                        document.data["instagram"].toString(),
                        document.data["facility"].toString(),
                        document.data["time"].toString()
                    )
                if(data.isfav == "True") {
                    favList.add(data)
                }
            }
            TampilkanData()
        }
    }
    private fun TampilkanData() {
        _rvFav.layoutManager = LinearLayoutManager(requireContext())
        val adapterP = CoachListAdapter(favList)
        _rvFav.adapter = adapterP
    }

}