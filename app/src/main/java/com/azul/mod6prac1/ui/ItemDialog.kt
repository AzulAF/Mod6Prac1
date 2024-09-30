package com.azul.mod6prac1.ui

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.azul.mod6prac1.R
import com.azul.mod6prac1.application.ItemsDPApp
import com.azul.mod6prac1.data.ItemRepository
import com.azul.mod6prac1.data.db.model.ItemEntity
import com.azul.mod6prac1.databinding.ItemDialogBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class ItemDialog (
    private val newItem: Boolean = true,
    private var item: ItemEntity = ItemEntity(
        merch = "",
        cost = "",
        table = "",
        tag = "",
        name = "",
        type = "",
        merchPriority = ""
    ),
    private val updateUI: () -> Unit,
    private val message: (String) -> Unit
    ): DialogFragment() {
    private var _binding: ItemDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: Dialog

    private var saveButton: Button? = null

    private lateinit var repository: ItemRepository

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ItemDialogBinding.inflate(requireActivity().layoutInflater)
        repository = (requireContext().applicationContext as ItemsDPApp).repository
        builder = AlertDialog.Builder(requireContext())

        //val merchpriorityOptions = arrayOf("Opciones","ALTA", "MEDIA", "BAJA")
        val merchpriorityOptions: Array<String> = resources.getStringArray(R.array.merchpriority_options)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, merchpriorityOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMerchPriority.adapter = spinnerAdapter

        val merchTypeOptions: Array<String> = resources.getStringArray(R.array.merchtype_options)
        //val merchTypeOptions = arrayOf("Opciones","LLavero", "Bolsa", "Playera", "Stickers", "Poster")
        val spinner2Adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, merchTypeOptions)
        spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMerchType.adapter = spinner2Adapter
        var selectedOptionSpinnerType : String = ""
        var selectedOptionSpinnerPriority: String = ""


        binding.apply {
            tietMerch.setText(item.merch)
            tietCost.setText(item.cost)
            tietTable.setText(item.table)
            tietTag.setText(item.tag)
            tietArtistName.setText(item.name)

            //IMAGE NAME
        }
        //SPINNER FUNCTIONALITY
        binding.spinnerMerchType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedOptionSpinnerType = parent.getItemAtPosition(position).toString()
                Log.d("SpinnerSelectionTYPE", "Selected option: $selectedOptionSpinnerType")
                if(selectedOptionSpinnerType != context?.getString(R.string.options))
                    item.type = selectedOptionSpinnerType
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("SpinnerSelectionTYPE", "No option selected")
            }
        }

        binding.spinnerMerchPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedOptionSpinnerPriority = parent.getItemAtPosition(position).toString()
                Log.d("SpinnerSelectionPRIORITY", "Selected option: $selectedOptionSpinnerPriority")
                if(selectedOptionSpinnerPriority != context?.getString(R.string.options))
                    item.merchPriority = selectedOptionSpinnerPriority
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("SpinnerSelectionPRIORITY", "No option selected")
            }
        }


        //Se intento quitar el hardcode de los siguientes strings, pero no fue posible por medio de context.getString(R.string.save)
        dialog = if(newItem)
            buildDialog("SAVE", "CANCEL", {
                //Guardar
                binding.apply {
                    item.apply {
                        merch = tietMerch.text.toString()
                        cost = tietCost.text.toString()//.toInt()
                        table = tietTable.text.toString()//.toInt()
                        tag = tietTag.text.toString()
                        name = tietArtistName.text.toString()
                        if(selectedOptionSpinnerPriority != context?.getString(R.string.options))
                            merchPriority = selectedOptionSpinnerPriority
                        if(selectedOptionSpinnerType != context?.getString(R.string.options))
                            type = selectedOptionSpinnerType

                    }
                    //IMAGE NAME
                }

                try{

                    lifecycleScope.launch(Dispatchers.IO) {
                        val result = async {
                            repository.insertItem(item)
                        }

                        //Con esto nos esperamos a que se termine esta acción antes de ejecutar lo siguiente
                        result.await()

                        //Con esto mandamos la ejecución de message y updateUI al hilo principal
                        withContext(Dispatchers.Main){
                            message(getString(R.string.savedItem))
                            updateUI()
                        }
                    }

                }catch (e: IOException){
                    message(getString(R.string.errorsavedItem))
                }

            }, {
                //Accion de cancelar
        })
        else
            buildDialog("ACTUALIZAR", "BORRAR", {
                binding.apply {
                    item.apply {
                        merch = tietMerch.text.toString()
                        cost = tietCost.text.toString()//.toInt()
                        table = tietTable.text.toString()//.toInt()
                        tag = tietTag.text.toString()
                        name = tietArtistName.text.toString()
                        if(selectedOptionSpinnerPriority != getString(R.string.options))
                            merchPriority = selectedOptionSpinnerPriority
                        if(selectedOptionSpinnerType != getString(R.string.options))
                            type = selectedOptionSpinnerType
                    }
                    //IMAGE NAME
                }
                try {

                    lifecycleScope.launch(Dispatchers.IO) {
                        val result = async {
                            repository.updateItem(item)
                        }

                        result.await()

                        withContext(Dispatchers.Main) {
                            message(getString(R.string.updatedItem))

                            updateUI()
                        }
                    }


                } catch (e: IOException) {

                    message(getString(R.string.errorupdatedItem))

                }
            }, {
                //Acción de borrar

                //Almacenamos el contexto en una variable antes de mandar llamar el diálogo nuevo
                val context = requireContext()

                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.confirm))
                    .setMessage(getString(R.string.confirm_item, item.merch))
                    //¿Realmente desea eliminar el juego %1$s?
                    .setPositiveButton(getString(R.string.ok)){ _, _ ->
                        try{
                            lifecycleScope.launch(Dispatchers.IO) {
                                val result = async {
                                    repository.deleteItem(item)
                                }
                                result.await()
                                withContext(Dispatchers.Main){
                                    message(context.getString(R.string.erasingItem))
                                    updateUI()
                                }
                            }
                        }catch (e: IOException){
                            message(getString(R.string.errorerasingItem))
                        }
                    }
                    .setNegativeButton(getString(R.string.cancelar)){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .create().show()
            })
      return dialog
    }

    //Destruir
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //Despues de llamar al dialog en pantalla
    override fun onStart() {
        super.onStart()
        //Debido a que la clase dialog no me permite referenciarme a sus botones
        val alertDialog = dialog as AlertDialog
        saveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        saveButton?.isEnabled = false

        binding.apply {
            setupTextWatcher(
                tietMerch,
                tietCost,
                tietTable,
                tietTag,
                tietArtistName
            )
        }

    }

    private fun validateFields(): Boolean
            = binding.tietMerch.text.toString().isNotEmpty() &&
            binding.tietCost.text.toString().isNotEmpty() &&
            binding.tietTable.text.toString().isNotEmpty() &&
            binding.tietTag.text.toString().isNotEmpty() &&
            binding.tietArtistName.text.toString().isNotEmpty()


//A FUTURO, UTILIZAR EL NUMERO DE MESA PARA TRAER EL NOMBRE DEL ARTISTA
    //Para después

    private fun setupTextWatcher(vararg textFields: TextInputEditText){
        val textWatcher = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()
            }
        }

        textFields.forEach { textField ->
            textField.addTextChangedListener(textWatcher)
        }
    }


    private fun buildDialog(
        btn1Text: String,
        btn2Text: String,
        positiveButton: () -> Unit,
        negativeButton: () -> Unit
    ): Dialog {
        return builder.setView(binding.root)
            .setTitle(R.string.texto_item)
            .setPositiveButton(btn1Text){ _, _ ->
                //Acción para el botón positivo
                positiveButton()
            }.setNegativeButton(btn2Text){ _, _ ->
                //Acción para el botón negativo
                negativeButton()
            }
            .create()
    }


}