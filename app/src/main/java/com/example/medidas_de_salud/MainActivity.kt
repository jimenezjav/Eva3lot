package com.example.medidas_de_salud

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.medidas_de_salud.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

// Clase principal de la aplicación, representa la pantalla de inicio de sesión
class MainActivity : AppCompatActivity() {

    // Configuración de ViewBinding para acceder a los elementos de la interfaz gráfica
    private lateinit var binding: ActivityMainBinding
    // Configuración de Firebase Authentication para la autenticación de usuarios
    private lateinit var auth: FirebaseAuth

    // Método que se ejecuta al iniciar la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Activa compatibilidad con diseño edge-to-edge

        // Inicializar ViewBinding para asociar el diseño de la actividad
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Asigna el diseño como la vista principal

        // Ajusta los márgenes de la vista para que no interfieran con las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar Firebase Authentication
        auth = Firebase.auth

        // Configuración del botón de inicio de sesión
        binding.btnLogin.setOnClickListener {
            // Obtiene el correo electrónico y contraseña ingresados por el usuario
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Valida que el campo de correo electrónico no esté vacío
            if (email.isEmpty()) {
                binding.etEmail.error = "Por favor ingrese un correo" // Muestra un error en el campo
                return@setOnClickListener // Detiene la ejecución si el campo está vacío
            }

            // Valida que el campo de contraseña no esté vacío
            if (password.isEmpty()) {
                binding.etPassword.error = "Por favor ingrese la contraseña" // Muestra un error en el campo
                return@setOnClickListener // Detiene la ejecución si el campo está vacío
            }

            // Llama al método para iniciar sesión con las credenciales ingresadas
            signIn(email, password)
        }

        // Configuración del enlace para ir a la pantalla de registro
        binding.tvRegistrar.setOnClickListener {
            try {
                // Crea una intención para abrir la actividad de registro
                val intent = Intent(this, RegistrarActivity::class.java)
                startActivity(intent) // Inicia la actividad de registro
            } catch (e: Exception) {
                // Muestra un mensaje de error si ocurre un problema
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para iniciar sesión con Firebase Authentication
    private fun signIn(email: String, password: String) {
        // Intenta iniciar sesión con el correo electrónico y contraseña proporcionados
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Si el inicio de sesión es exitoso, muestra un mensaje al usuario
                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()

                    // Intenta abrir la actividad post-login
                    try {
                        val intent = Intent(this, PostLogin::class.java)
                        startActivity(intent) // Inicia la actividad post-login
                    } catch (e: Exception) {
                        // Muestra un mensaje de error si ocurre un problema
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Si el inicio de sesión falla, muestra un mensaje de error
                    Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

