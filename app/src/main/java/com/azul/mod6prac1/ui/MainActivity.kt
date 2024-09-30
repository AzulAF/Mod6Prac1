package com.azul.mod6prac1.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.azul.mod6prac1.R
import com.azul.mod6prac1.application.ItemsDPApp
import com.azul.mod6prac1.data.ItemRepository
import com.azul.mod6prac1.data.db.model.ItemEntity
import com.azul.mod6prac1.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var items: MutableList<ItemEntity> = mutableListOf()
    private lateinit var repository: ItemRepository
    private lateinit var itemsAdapter: ItemAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = (application as ItemsDPApp).repository

        itemsAdapter = ItemAdapter{ selectedItem->
            //Registro de un item
            val dialog = ItemDialog(newItem = false, item = selectedItem, updateUI = {
                updateUI()
            }, message = { text ->
                message(text)
            })
            dialog.show(supportFragmentManager, "dialog2")
        }

        //El recyclerview
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = itemsAdapter
        }


        updateUI()
    }

    fun click(view: View) {
        //Manejamos el click del floating action button

        val dialog = ItemDialog(updateUI = {
            updateUI()
        }, message = { text ->
            //Aquí va el mensaje
            message(text)

        })

        dialog.show(supportFragmentManager, "dialog1")

    }

    private fun message(text: String){
        Snackbar.make(
            binding.cl,
            text,
            Snackbar.LENGTH_SHORT
        )
            .setTextColor(getColor(R.color.white))
            .setBackgroundTint(getColor(R.color.snackbar))
            .show()
        //#9E1734
    }

    private fun updateUI(){
        lifecycleScope.launch {
            items = repository.getAllItems()
            binding.tvSinRegistros.visibility =
                if(items.isNotEmpty()) View.INVISIBLE else View.VISIBLE
            itemsAdapter.updateList(items)
        }
    }



}