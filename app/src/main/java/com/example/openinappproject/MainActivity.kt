package com.example.openinappproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.example.openinappproject.databinding.ActivityMainBinding
import com.example.openinappproject.fragments.LinksFragment

class MainActivity : AppCompatActivity() {
    private lateinit var fm : FragmentManager
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        enableEdgeToEdge()
        window.statusBarColor = resources.getColor(R.color.blue)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fm = supportFragmentManager
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fm.beginTransaction().replace(R.id.container,LinksFragment()).commit()
    }
}