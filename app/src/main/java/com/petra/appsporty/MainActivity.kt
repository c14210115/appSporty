package com.petra.appsporty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}