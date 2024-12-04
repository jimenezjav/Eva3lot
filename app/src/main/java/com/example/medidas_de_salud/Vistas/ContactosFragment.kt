package com.example.medidas_de_salud.Vistas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medidas_de_salud.R
import com.example.medidas_de_salud.databinding.FragmentContactosBinding
import com.example.medidas_de_salud.databinding.ItemContactoBinding

class ContactosFragment : Fragment() {

    private lateinit var binding: FragmentContactosBinding
    private val contactosList = mutableListOf<Contact>()
    private lateinit var contactosAdapter: ContactosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactosBinding.inflate(inflater, container, false)

        // Configurar RecyclerView
        binding.recyclerViewContactos.layoutManager = LinearLayoutManager(context)
        contactosAdapter = ContactosAdapter(contactosList)
        binding.recyclerViewContactos.adapter = contactosAdapter

        // Botón para agregar contacto
        binding.btnAgregarContacto.setOnClickListener {
            val nombre = binding.editTextNombreContacto.text.toString()
            val telefono = binding.editTextTelefonoContacto.text.toString()

            if (nombre.isNotEmpty() && telefono.isNotEmpty()) {
                // Agregar contacto a la lista
                val contacto = Contact(nombre, telefono)
                contactosList.add(contacto)
                contactosAdapter.notifyDataSetChanged()

                // Limpiar los campos
                binding.editTextNombreContacto.text.clear()
                binding.editTextTelefonoContacto.text.clear()
            } else {
                Toast.makeText(context, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    // Clase de modelo para los contactos
    data class Contact(val nombre: String, val telefono: String)

    // Adaptador para RecyclerView
    class ContactosAdapter(private val contactos: List<Contact>) : RecyclerView.Adapter<ContactosAdapter.ContactViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
            val binding = ItemContactoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ContactViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
            val contacto = contactos[position]
            holder.bind(contacto)
        }

        override fun getItemCount(): Int = contactos.size

        // ViewHolder para el RecyclerView
        class ContactViewHolder(private val binding: ItemContactoBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(contacto: Contact) {
                binding.textViewNombre.text = contacto.nombre
                binding.textViewTelefono.text = contacto.telefono
            }
        }
    }
}
