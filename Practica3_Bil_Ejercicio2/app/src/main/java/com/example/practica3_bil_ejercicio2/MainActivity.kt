 package com.example.practica3_bil_ejercicio2

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.practica3_bil_ejercicio2.databinding.ActivityMainBinding

 class MainActivity : AppCompatActivity() {
     private lateinit var binding: ActivityMainBinding
     private lateinit var viewModel: MainActivityViewModel
     private lateinit var texto: String
     val numeros = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inicializarBinding()
        inicializarViewModel()
        inicializarBotones()
        if (viewModel.numeroRandom == -1)
            jugar()
        else
            binding.txtVidas.text = "Número de vidas: "+viewModel.numeroVidas.toString()
    }

    private fun inicializarBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun inicializarViewModel() {
        // OBTIENE UN MainActivityViewModel PARA THIS (MainActivity)
        // LA PRIMERA VEZ QUE SE LLAMA AL MÉTODO, DEVUELVE UN MainActivityViewModel NUEVO
        // SI SE GIRA EL MÓVIL, LA SIGUIENTE QUE SE LLAMA DEVUELVE EL QUE YA HABÍA
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

     private fun inicializarBotones() {
         binding.btnComprobar.setOnClickListener{
             comprobarNumero(binding.editTxtNumero)
         }
         binding.btnReiniciar.setOnClickListener{
             jugar()
             binding.txtVidas.text = "Vuelves a tener 4 vidas. Nuevo número secreto seleccionado"
         }
     }

     private fun elegirNumeroRandom(numeros : Array<Int>) {
         viewModel.numeroRandom = numeros.random()
     }

     private fun comprobarNumero(editTextNumero: EditText) {
         val numeroTexto = editTextNumero.text.toString()

         if (numeroTexto.isNotEmpty()) {
             val numero = numeroTexto.toInt()

             if (numero == viewModel.numeroRandom) {
                 crearToast("¡Enhorabuena, has ganado! Con " + viewModel.numeroVidas + " VIDAS restantes")
                 binding.btnComprobar.isEnabled = false
                 mostrarBtnReiniciar()

             } else {
                 if (viewModel.numeroVidas <= 0) {
                     binding.btnComprobar.isEnabled = false
                     crearToast("¡Te has quedado sin vidas! El número era: " + viewModel.numeroRandom)
                     mostrarBtnReiniciar()
                 } else {
                     viewModel.numeroVidas--
                     binding.txtVidas.text = "Número de vidas: "+viewModel.numeroVidas.toString()

                     if (numero > viewModel.numeroRandom)
                         texto = "El número secreto es más pequeño que el introducido"
                     else
                         texto = "El número secreto es más grande que el introducido"

                     crearToast(texto)
                 }
             }
         } else {
             crearToast("Por favor, introduce un número válido.")
         }
     }

     private fun crearToast(texto: String) {
         Toast.makeText(
             this,
             texto,
             Toast.LENGTH_LONG
         ).show()
     }

     private fun mostrarBtnReiniciar(){
         binding.btnReiniciar.alpha = 1F
         binding.btnReiniciar.isEnabled = true
     }

     private fun jugar() {
         elegirNumeroRandom(numeros)
         viewModel.numeroVidas = 4
         binding.btnReiniciar.isEnabled = false
         binding.btnReiniciar.alpha = 0F
         binding.btnComprobar.isEnabled =true

     }
 }
