package com.example.stacjapogodowa

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.stacjapogodowa.databinding.ActivityMainBinding
import com.example.stacjapogodowa.fragments.GraphsFragment
import com.example.stacjapogodowa.fragments.HomeFragment
import com.example.stacjapogodowa.fragments.NotificationsFragment
import com.example.stacjapogodowa.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.home -> replaceFragment(HomeFragment())
                    R.id.graphs -> replaceFragment(GraphsFragment())
                    R.id.settings -> replaceFragment(SettingsFragment())
                    R.id.notifications -> replaceFragment(NotificationsFragment())

                    else ->{

                    }

                }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}