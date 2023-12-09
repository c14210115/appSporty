package com.petra.appsporty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_editProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_editProfile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var editTextName: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextExperience: EditText
    private lateinit var buttonSave: Button


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
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        editTextName = view.findViewById(R.id.editTextName)
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber)
        editTextAge = view.findViewById(R.id.editTextAge)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextExperience = view.findViewById(R.id.editTextExperience)
        buttonSave = view.findViewById(R.id.buttonSave)

        // Menyimpan perubahan ketika tombol Simpan diklik
        buttonSave.setOnClickListener {
            saveEditedData()
            navigateBackToProfile()
        }
        return view

    }
    private fun displayProfileData() {
        //coba" aku liat d yt ktny pake ini bgs buat profile
        val sharedPrefs = requireActivity().getPreferences(android.content.Context.MODE_PRIVATE)
        editTextName.setText(sharedPrefs.getString("name", ""))
        editTextPhoneNumber.setText(sharedPrefs.getString("phoneNumber", ""))
        editTextAge.setText(sharedPrefs.getString("age", ""))
        editTextEmail.setText(sharedPrefs.getString("email", ""))
        editTextExperience.setText(sharedPrefs.getString("experience", ""))
    }

    private fun saveEditedData() {
        val sharedPrefs = requireActivity().getPreferences(android.content.Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        editor.putString("name", editTextName.text.toString())
        editor.putString("phoneNumber", editTextPhoneNumber.text.toString())
        editor.putString("age", editTextAge.text.toString())
        editor.putString("email", editTextEmail.text.toString())
        editor.putString("experience", editTextExperience.text.toString())

        editor.apply()
    }

    private fun navigateBackToProfile() {
        //balik ke halaman sblme
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_editProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_editProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}