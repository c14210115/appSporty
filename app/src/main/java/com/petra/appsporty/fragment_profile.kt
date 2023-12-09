package com.petra.appsporty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var textViewName: TextView
    private lateinit var textViewPhoneNumber: TextView
    private lateinit var textViewAge: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewExperience: TextView
    private lateinit var buttonEdit: Button

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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        textViewName = view.findViewById(R.id.textViewName)
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber)
        textViewAge = view.findViewById(R.id.textViewAge)
        textViewEmail = view.findViewById(R.id.textViewEmail)
        textViewExperience = view.findViewById(R.id.textViewExperience)
        buttonEdit = view.findViewById(R.id.buttonEdit)

        //Data profile ny d tmpilin
        displayProfileData()

        //ButtonEdit
        buttonEdit.setOnClickListener {
            navigateToEditProfile()
        }

        return view
    }
    private fun displayProfileData() {
        val sharedPrefs = requireActivity().getPreferences(android.content.Context.MODE_PRIVATE)
        textViewName.text = "Nama: ${sharedPrefs.getString("name", "")}"
        textViewPhoneNumber.text = "Nomor Telepon: ${sharedPrefs.getString("phoneNumber", "")}"
        textViewAge.text = "Umur: ${sharedPrefs.getString("age", "")}"
        textViewEmail.text = "Email: ${sharedPrefs.getString("email", "")}"
        textViewExperience.text = "Pengalaman: ${sharedPrefs.getString("experience", "")}"
    }
    private fun navigateToEditProfile() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val editProfileFragment = fragment_editProfile()

        //buat masukin frgmentny
        fragmentTransaction.replace(R.id.fragmentContainerView, editProfileFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}