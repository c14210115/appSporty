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
            //nanti panggil halaman list nya d sni
        }

        binding.navBarFav.setOnClickListener {
            //nanti panggil halaman favorit nya di sni
        }

    }

    private fun goToFragment(fragment: Fragment) {
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
    }
}