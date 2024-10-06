package com.azul.mod6prac1.ui.fragments

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azul.mod6prac1.R
import com.azul.mod6prac1.application.ItemsDPApp
import com.azul.mod6prac1.data.ItemRepository
import com.azul.mod6prac1.data.network.model.ItemDetailDto
import com.azul.mod6prac1.databinding.FragmentItemDetailBinding
import com.azul.mod6prac1.util.Constants
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ITEM_ID = "item_id"

class ItemDetailFragment : Fragment() {

    private var itemId: String? = null

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get()  = _binding!!

    private lateinit var repository: ItemRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            itemId = args.getString(ITEM_ID)
            Log.d(Constants.LOGTAG, "Id recibido $itemId")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    //Se manda llamar ya cuando el fragment es visible en pantalla
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Obteniendo la instancia al repositorio
        repository = (requireActivity().application as ItemsDPApp).repository

        itemId?.let{ id ->
            //Hago la llamada al endpoint para consumir los detalles del juego

            //val call: Call<GameDetailDto> = repository.getGameDetail(id)

            //Para apiary
            val call: Call<ItemDetailDto>? = repository.getItemDetailApiary(id)

            if (call != null) {
                call.enqueue(object: Callback<ItemDetailDto>{
                    override fun onResponse(p0: Call<ItemDetailDto>, response: Response<ItemDetailDto>) {

                        binding.apply {
                            pbLoading.visibility = View.GONE

                            //Aquí utilizamos la respuesta exitosa y asignamos los valores a las vistas
                            tvName.text = response.body()?.nombre

                            Glide.with(requireActivity())
                                .load(response.body()?.imagen)
                                .into(ivImage)

                            /*Picasso.get()
                                    .load(response.body()?.image)
                                    .into(ivImage)*/

                            tvPiso.text = response.body()?.piso
                            tvMesa.text = response.body()?.mesa
                            tvSellos.text = response.body()?.sellos
                            tvTarjeta.text = response.body()?.pagotarjeta
                            tvEfectivo.text = response.body()?.pagoefectivo
                            tvTransferencia.text = response.body()?.transferecia

                           }


                    }

                    override fun onFailure(p0: Call<ItemDetailDto>, p1: Throwable) {
                        //Manejo del error de conexión
                    }

                })
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(itemId: String) =
            ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM_ID, itemId)
                }
            }
    }

}