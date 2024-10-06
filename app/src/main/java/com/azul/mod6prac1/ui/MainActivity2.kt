package com.azul.mod6prac1.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.azul.mod6prac1.R
import com.azul.mod6prac1.databinding.ActivityMain2Binding
import com.azul.mod6prac1.ui.fragments.ItemListFragment

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        //Mostramos el fragment inicial ItemsListFragment
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ItemListFragment())
                .commit()
        }
    }
}