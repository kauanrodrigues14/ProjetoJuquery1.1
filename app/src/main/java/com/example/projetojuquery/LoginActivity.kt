package com.example.projetojuquery


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogar = findViewById<Button>(R.id.btnLogar)
        val bd = bdConnect(this)
        val bdRead = bd.readableDatabase

        /*
        Teste de conexão com o banco
        val cursor = bdRead.rawQuery("SELECT * FROM Bombeiro", null)

        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                Log.d("Conexão com o Banco de Dados", "Conexão efetivada. Nome do primeiro bombeiro: $nome")
            } else {
                Log.d("Conexão com o Banco de Dados", "Conexão efetivada, mas não há bombeiros na tabela.")
            }
            cursor.close()
        } else {
            Log.d("Conexão com o Banco de Dados", "Falha ao efetivar a conexão.")
        }
*/      val edtLogin = findViewById<EditText>(R.id.editText)
        val edtSenha = findViewById<EditText>(R.id.edtSenha)

        btnLogar.setOnClickListener {

            Log.d("DEBUG", "Botão de login pressionado")


            val login = edtLogin.text.toString()
            val senha = edtSenha.text.toString()



            if (login.isBlank() || senha.isBlank()) {
                Toast.makeText(this, "Login e senha não podem estar vazios.", Toast.LENGTH_SHORT).show()
                edtLogin.text.clear()
                edtSenha.text.clear()
                edtLogin.requestFocus()
            } else {
                val autenticado = bd.autenticarUsuario(login, senha)

                if (autenticado) {
                    Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, PaginaInicial::class.java)
                    startActivity(intent)
                } else {
                    edtLogin.text.clear()
                    edtSenha.text.clear()
                    edtLogin.requestFocus()
                    Toast.makeText(this, "Falha no login. Por favor, tente novamente.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val txtCadastro = findViewById<TextView>(R.id.txtCadastro)
        txtCadastro.setOnClickListener{
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
        }


    }
}











