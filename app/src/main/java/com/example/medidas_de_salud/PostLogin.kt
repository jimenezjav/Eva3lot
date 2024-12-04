package com.example.medidas_de_salud

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.medidas_de_salud.Vistas.AlertasFragment
import com.example.medidas_de_salud.Vistas.ContactosFragment
import com.example.medidas_de_salud.Vistas.InicioFragment
import com.example.medidas_de_salud.Vistas.MonitoreoFragment
import com.example.medidas_de_salud.Vistas.PerfilFragment
import com.example.medidas_de_salud.databinding.ActivityMainBinding
import com.example.medidas_de_salud.databinding.ActivityPostLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

// Clase para manejar la actividad post-login del usuario
class PostLogin : AppCompatActivity() {
    // Activar ViewBinding para acceder de forma segura a los elementos de la interfaz gráfica
    private lateinit var binding: ActivityPostLoginBinding

    // Configurar Firebase Authentication para la autenticación del usuario
    private lateinit var auth: FirebaseAuth

    // Método que se ejecuta al iniciar la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Activa la compatibilidad con el diseño edge-to-edge

        // Inicializar ViewBinding para vincular el diseño de la actividad
        binding = ActivityPostLoginBinding.inflate(layoutInflater)
        setContentView(binding.root) // Asigna el diseño a la actividad

        // Configura los márgenes para ajustar las barras del sistema en dispositivos modernos
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar Firebase Authentication
        auth = Firebase.auth

        // Cargar un fragmento por defecto (InicioFragment) al iniciar la actividad si no hay un estado guardado
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, InicioFragment()).commit()
        }

        // Configura el listener para manejar las selecciones del menú de navegación inferior
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_1 -> {
                    // Cargar el fragmento de inicio
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, InicioFragment()).commit()
                    true
                }
                R.id.item_2 -> {
                    // Cargar el fragmento de monitoreo
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MonitoreoFragment()).commit()
                    true
                }
                R.id.item_3 -> {
                    // Cargar el fragmento de contactos
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ContactosFragment()).commit()
                    true
                }
                R.id.item_4 -> {
                    // Cargar el fragmento de alertas
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AlertasFragment()).commit()
                    true
                }
                R.id.item_5 -> {
                    // Cargar el fragmento de perfil
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PerfilFragment()).commit()
                    true
                }
                else -> false
            }
        }

        // Configura el listener para manejar re-selecciones de elementos en el menú de navegación inferior
        binding.bottomNavigation.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.item_1 -> true // No realiza ninguna acción adicional al re-seleccionar el elemento
                R.id.item_2 -> true
                R.id.item_3 -> true
                R.id.item_4 -> true
                R.id.item_5 -> true
                else -> false
            }
        }

        // Configura el botón de cierre de sesión
        binding.btnLogout.setOnClickListener {
            // Muestra un cuadro de diálogo para confirmar la acción de cerrar sesión
            MaterialAlertDialogBuilder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro que deseas cerrar sesión?")
                .setNeutralButton("Cancelar") { dialog, which ->
                    // No realiza ninguna acción si se presiona "Cancelar"
                }
                .setPositiveButton("Aceptar") { dialog, which ->
                    // Cierra la sesión si se presiona "Aceptar"
                    signOut()
                }
                .show()
        }
    }

    // Método para cerrar sesión
    private fun signOut() {
        Firebase.auth.signOut() // Cierra la sesión del usuario en Firebase
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show() // Muestra un mensaje de confirmación
        finish() // Finaliza la actividad actual
    }
}
