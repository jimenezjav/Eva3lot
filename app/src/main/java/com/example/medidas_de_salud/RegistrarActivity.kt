package com.example.medidas_de_salud

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.medidas_de_salud.databinding.ActivityPostLoginBinding
import com.example.medidas_de_salud.databinding.ActivityRegistrarBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

// Clase para gestionar la actividad de registro
class RegistrarActivity : AppCompatActivity() {

    // Configuración de ViewBinding para acceder a los elementos de la interfaz gráfica de manera segura
    private lateinit var binding: ActivityRegistrarBinding

    // Configuración de Firebase Authentication para la autenticación de usuarios
    private lateinit var auth: FirebaseAuth

    // Método que se ejecuta al iniciar la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el diseño de bordes en dispositivos modernos

        // Inicializa el binding con el diseño asociado a esta actividad
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root) // Asigna el diseño a la vista principal de la actividad

        // Ajusta las barras del sistema para manejar correctamente los márgenes de la pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa Firebase Authentication
        auth = Firebase.auth

        // Configura el listener del botón de registro
        binding.btnRegistrar.setOnClickListener {
            // Obtiene el correo electrónico ingresado por el usuario
            val email = binding.etEmail.text.toString()
            // Obtiene las contraseñas ingresadas por el usuario
            val pass1 = binding.etPassword.text.toString()
            val pass2 = binding.etPassword2.text.toString()

            // Validaciones para asegurarse de que los campos no estén vacíos
            if (email.isEmpty()) {
                binding.etEmail.error = "Por favor ingrese su correo"
                return@setOnClickListener
            }
            if (pass1.isEmpty()) {
                binding.etPassword.error = "Por favor ingrese una contraseña"
                return@setOnClickListener
            }
            if (pass2.isEmpty()) {
                binding.etPassword2.error = "Por favor ingrese la contraseña nuevamente"
                return@setOnClickListener
            }
            // Validación para comprobar que ambas contraseñas coincidan
            if (pass1 != pass2) {
                binding.etPassword2.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            } else {
                // Llama al método para registrar al usuario si todas las validaciones son correctas
                signUp(email, pass1)
            }
        }
    }

    // Método para registrar un nuevo usuario en Firebase Authentication
    private fun signUp(email: String, pass1: String) {
        auth.createUserWithEmailAndPassword(email, pass1)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Muestra un mensaje de éxito si el registro se realiza correctamente
                    Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                    // Redirige al usuario a la actividad principal
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // Muestra un mensaje de error si ocurre algún problema durante el registro
                    Toast.makeText(this, "Error en el registro del usuario", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
