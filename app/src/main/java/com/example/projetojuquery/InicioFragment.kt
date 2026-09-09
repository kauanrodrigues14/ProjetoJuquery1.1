package com.example.projetojuquery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class InicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_inicio, container, false)


    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var alertaViewAdapter: AlertaViewAdapter
    private lateinit var alertaList: List<bdConnect.Alerta>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contexto = requireContext()

        recyclerView = view.findViewById(R.id.recyclerViewAlertas)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val bdHelper = bdConnect(requireContext())
        alertaList = bdHelper.obterAlertas()

        alertaViewAdapter = AlertaViewAdapter(alertaList)
        recyclerView.adapter = alertaViewAdapter


        val edtSenhaAdm = view.findViewById<EditText>(R.id.edtSenhaAdm)
        val edtLoginAdm = view.findViewById<EditText>(R.id.edtLoginAdm)
        val btnLoginAdm = view.findViewById<Button>(R.id.btnLoginAdm)

        btnLoginAdm.setOnClickListener {
            val login = edtLoginAdm.text.toString()
            val senha = edtSenhaAdm.text.toString()

            if (login == "adm" && senha == "adm") {
                Toast.makeText(contexto, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                val intent = Intent(contexto, AdmInicial::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(contexto, "Credenciais inválidas.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    }

