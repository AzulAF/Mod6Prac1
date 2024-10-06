package com.azul.mod6prac1.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.azul.mod6prac1.R
import com.azul.mod6prac1.application.ItemsDPApp
import com.azul.mod6prac1.data.ItemRepository
import com.azul.mod6prac1.data.network.model.ItemOutsideDto
import com.azul.mod6prac1.databinding.FragmentItemListBinding
import com.azul.mod6prac1.ui.adapters.ItemListAdapter

//
class ItemListFragment : Fragment() {
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ItemRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Obteniendo la instancia al repositorio
        repository = (requireActivity().application as ItemsDPApp).repository

        //val call: Call<MutableList<GameDto>> = repository.getGames("cm/games/games_list.php")

        //Para apiary
        val call: Call<MutableList<ItemOutsideDto>>? = repository.getItemsApiary()

        if (call != null) {
            call.enqueue(object: Callback<MutableList<ItemOutsideDto>>{
                override fun onResponse(
                    p0: Call<MutableList<ItemOutsideDto>>,
                    response: Response<MutableList<ItemOutsideDto>>
                ) {
                    binding.pbLoading.visibility = View.GONE

                    response.body()?.let{ items ->

                        //Le pasamos los juegos al recycler view y lo instanciamos
                        binding.rvItems.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            //layoutManager = GridLayoutManager(requireContext(), 3)
                            adapter = ItemListAdapter(items){ item ->
                                //Aquí realizamos la acción para ir a ver los detalles del juego
                                item.id?.let{ id ->
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, ItemDetailFragment.newInstance(id))
                                        .addToBackStack(null)
                                        .commit()
                                }
                            }
                        }

                    }
                }

                override fun onFailure(p0: Call<MutableList<ItemOutsideDto>>, p1: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Error: No hay conexión disponible",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.pbLoading.visibility = View.GONE
                }

            })
        }

        fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
    }


}