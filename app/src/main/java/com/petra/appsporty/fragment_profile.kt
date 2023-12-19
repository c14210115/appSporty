package com.petra.appsporty

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

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
    private lateinit var firestore: FirebaseFirestore
    private lateinit var textViewName: TextView
    private lateinit var textViewPhoneNumber: TextView
    private lateinit var textViewAge: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewCategory: TextView
    private lateinit var textViewGender: TextView
    private lateinit var textViewDescription: TextView
    var username: String? = ""

    private lateinit var buttonEdit: Button
    private lateinit var buttonLogout: Button
    val dbProfile = Firebase.firestore
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
        firestore = FirebaseFirestore.getInstance()
        textViewName = view.findViewById(R.id.textViewName)
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber)
        textViewAge = view.findViewById(R.id.textViewAge)
        textViewEmail = view.findViewById(R.id.textViewEmail)
        textViewGender = view.findViewById(R.id.textViewGender)
        textViewCategory = view.findViewById(R.id.textViewCategory)
        textViewDescription = view.findViewById(R.id.textViewDescription)
        buttonEdit = view.findViewById(R.id.buttonEdit)


        //coba logout
        buttonLogout = view.findViewById(R.id.btnLogout)
        buttonLogout.setOnClickListener{
            //set username ke null
            if (activity is MainActivity) {
                val mainActivity = (activity as MainActivity?)
               mainActivity?.setMyUsername("")
            }

            val intentWithData = Intent(requireActivity(),LoginPage::class.java)
            startActivity(intentWithData)
        }
        //Data profile ny d tmpilin
        displayProfileData()

        //ButtonEdit
        buttonEdit.setOnClickListener {
            navigateToEditProfile()
        }

        return view
    }
    private fun displayProfileData() {
        //coba pake shrdprf
//        val sharedPrefs = requireActivity().getPreferences(android.content.Context.MODE_PRIVATE)
//        textViewName.text = "Nama: ${sharedPrefs.getString("name", "")}"
//        textViewPhoneNumber.text = "Nomor Telepon: ${sharedPrefs.getString("phoneNumber", "")}"
//        textViewAge.text = "Umur: ${sharedPrefs.getString("age", "")}"
//        textViewEmail.text = "Email: ${sharedPrefs.getString("email", "")}"
//        textViewExperience.text = "Pengalaman: ${sharedPrefs.getString("experience", "")}"
        var username: String? = ""
        if (activity is MainActivity) {
            val mainActivity = (activity as MainActivity?)
            username = mainActivity?.getMyUsername()
            Log.d("username", "${username}")
        }

        dbProfile.collection("users").document(username.toString())
            .get().addOnSuccessListener {document ->
                val user = User(
                    document.data!!.get("userName").toString(),
                    document.data!!.get("userAge").toString(),
                    document.data!!.get("userGender").toString(),
                    document.data!!.get("userTelp").toString(),
                    document.data!!.get("userEmail").toString(),
                    document.data!!.get("usercategory").toString(),
                    document.data!!.get("userNotes").toString(),
                    document.data!!.get("userPw").toString()

                )
                textViewName.text = user.userName
                textViewPhoneNumber.text = user.userTelp
                if(user.userAge != null.toString()) {
                    textViewAge.text = user.userAge
                }
                textViewEmail.text = user.userEmail
                if(user.userGender != null.toString()) {
                    textViewGender.text = user.userGender
                }
                if(user.usercategory != null.toString()) {
                    textViewCategory.text = user.usercategory
                }
                if(user.userNotes != null.toString()) {
                    textViewDescription.text = user.userNotes
                }

            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error getting documents: ", e)
            }
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