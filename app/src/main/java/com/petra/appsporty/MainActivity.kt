package com.petra.appsporty

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
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
        //untuk ikon
        val color = "#28D5F3"
        val colorInt = Color.parseColor(color)
        val colorStateList = ColorStateList.valueOf(colorInt)


        when (fragment) {
            is fragment_home -> {
                Log.d("HOME", "masuk")
                binding.navBarHome.backgroundTintList = colorStateList
                binding.navBarList.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
                binding.navBarFav.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)

                //untuk textnya diubah di bold
                binding.tvHomeBar.setTypeface(null, Typeface.BOLD)
                binding.tvListCoachBar.setTypeface(null, Typeface.NORMAL)
                binding.tvFavBar.setTypeface(null, Typeface.NORMAL)
            }
            is fragment_list_coach -> {
                binding.navBarHome.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
                binding.navBarList.backgroundTintList = colorStateList
                binding.navBarFav.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)

                binding.tvHomeBar.setTypeface(null, Typeface.NORMAL)
                binding.tvListCoachBar.setTypeface(null, Typeface.BOLD)
                binding.tvFavBar.setTypeface(null, Typeface.NORMAL)
            }
            is fragment_favorite -> {
                binding.navBarHome.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
                binding.navBarList.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
                binding.navBarFav.backgroundTintList = colorStateList

                binding.tvHomeBar.setTypeface(null, Typeface.NORMAL)
                binding.tvListCoachBar.setTypeface(null, Typeface.NORMAL)
                binding.tvFavBar.setTypeface(null, Typeface.BOLD)
            }
            is fragment_profile -> {
                binding.navBarHome.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
                binding.navBarList.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)
                binding.navBarFav.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.transparent)

                binding.tvHomeBar.setTypeface(null, Typeface.NORMAL)
                binding.tvListCoachBar.setTypeface(null, Typeface.NORMAL)
                binding.tvFavBar.setTypeface(null, Typeface.NORMAL)
            }
        }
    }
}