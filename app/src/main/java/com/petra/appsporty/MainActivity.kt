package com.petra.appsporty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.petra.appsporty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager : FragmentManager
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goToFragment(fragment_home())

        binding.navBarHome.setOnClickListener {
            goToFragment(fragment_home())
        }

        binding.imgProfile.setOnClickListener {
            goToFragment(fragment_profile())
        }

        binding.navBarList.setOnClickListener {
            goToFragment(fragment_list_coach())
        }

        binding.navBarFav.setOnClickListener {
            goToFragment(fragment_favorite())
        }

    }

    private fun goToFragment(fragment: Fragment) {
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()

        val pageTitle = when (fragment) {
            is fragment_home -> "SPORTY"
            is fragment_profile -> "PROFILE"
            is fragment_list_coach -> "COACH LIST"
            is fragment_favorite -> "FAVORITES"
            else -> "SPORTY" // Teks default jika tidak ada yang cocok
        }

        binding.textJudulHalaman.text = pageTitle

        //mengubah warna navbar dibawah
        when (fragment) {
            is fragment_home -> {
                Log.d("HOME", "masuk")
                binding.navBarHome.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.white)
                binding.navBarList.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
                binding.navBarFav.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
            }
            is fragment_list_coach -> {
                binding.navBarHome.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
                binding.navBarList.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.white)
                binding.navBarFav.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
            }
            is fragment_favorite -> {
                binding.navBarHome.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
                binding.navBarList.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
                binding.navBarFav.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.white)
            }
        }
    }
}