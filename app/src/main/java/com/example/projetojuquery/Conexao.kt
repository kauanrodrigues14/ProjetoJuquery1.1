package com.example.projetojuquery
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class bdConnect(contexto: Context) : SQLiteOpenHelper(contexto, NOME_DO_BANCO_DE_DADOS, null, VERSAO_DO_BANCO_DE_DADOS) {

    companion object {
        private const val VERSAO_DO_BANCO_DE_DADOS = 8
        private const val NOME_DO_BANCO_DE_DADOS = "projetojuquery.db"
    }

    override fun onCreate(bd: SQLiteDatabase) {
        val CRIAR_TABELA_BOMBEIRO = ("CREATE TABLE Bombeiro(" +
                "idbombeiro INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(50)," +
                "cpf CHAR(11)," +
                "login VARCHAR(15)," +
                "cargo VARCHAR(15)," +
                "senha VARCHAR(15))")

        val CRIAR_TABELA_SENSOR = ("CREATE TABLE Sensor(" +
                "idsensor INTEGER PRIMARY KEY AUTOINCREMENT," +
                "latitude CHAR(30)," +
                "longitude CHAR (30))")

        val CRIAR_TABELA_ALERTA = ("CREATE TABLE Alerta(" +
                "idalerta INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fk_idsensor INTEGER," +
                "datahora DATETIME," +
                "temperatura INTEGER," +
                "umidade INTEGER," +
                "fogo INTEGER," +
                "FOREIGN KEY (fk_idsensor) REFERENCES Sensor(idsensor))")

        bd.execSQL(CRIAR_TABELA_BOMBEIRO)
        bd.execSQL(CRIAR_TABELA_SENSOR)
        bd.execSQL(CRIAR_TABELA_ALERTA)

        val INSERIR_BOMBEIRO =
            ("INSERT INTO Bombeiro VALUES(\"1\",\"Bombeiro\",\"21312312312\",\"adm\",\"Bombeiro\",\"adm\")")
        bd.execSQL(INSERIR_BOMBEIRO)
        // Login de teste
    }
    override fun onUpgrade(bd: SQLiteDatabase, versaoAntiga: Int, novaVersao: Int) {
        bd.execSQL("DROP TABLE IF EXISTS Bombeiro")
        bd.execSQL("DROP TABLE IF EXISTS Sensor")
        bd.execSQL("DROP TABLE IF EXISTS Alerta")
        onCreate(bd)

    }

    fun cadastroBombeiro(nome: String, cpf: String, login: String, cargo: String, senha: String): Long {
        val bd = this.writableDatabase

        val valores = ContentValues().apply {
            put("nome", nome)
            put("cpf", cpf)
            put("login", login)
            put("cargo", cargo)
            put("senha", senha)
        }
        return bd.insert("Bombeiro", null, valores)
    }
    fun pegarDadosBombeiro(): List<Bombeiro> {
        val bd = this.readableDatabase
        val cursor = bd.rawQuery("SELECT * FROM Bombeiro", null)

        val bombeiros = mutableListOf<Bombeiro>()

        if (cursor.moveToFirst()) {
            do {
                val bombeiro = Bombeiro(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("idbombeiro")),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                    cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf")),
                    login = cursor.getString(cursor.getColumnIndexOrThrow("login")),
                    cargo = cursor.getString(cursor.getColumnIndexOrThrow("cargo")),

                )
                bombeiros.add(bombeiro)
            } while (cursor.moveToNext())
        }
        cursor.close()

        return bombeiros
    }
    fun atualizarBombeiro(id: Int, nome: String, cargo: String): Int {
        val bd = this.writableDatabase

        val valores = ContentValues().apply {
            put("nome", nome)
            put("cargo", cargo)
        }

        return bd.update("Bombeiro", valores, "idbombeiro = ?", arrayOf(id.toString()))
    }
    fun deletarBombeiro(id: Int):Int{

        val bd = this.writableDatabase
        return bd.delete("Bombeiro", "idbombeiro= ?", arrayOf(id.toString()))
    }
    data class Bombeiro(
        val id: Int,
        var nome: String,
        val cpf: String,
        val login: String,
        var cargo: String)



    fun cadastrarSensor(latitude: String, longitude: String): Long {
        val bd = this.writableDatabase

        val valores = ContentValues().apply {
            put("latitude", latitude)
            put("longitude", longitude)
        }

        return bd.insert("Sensor", null, valores)
    }
    fun atualizarSensor(id: Int, latitude: String, longitude: String): Int {
        val bd = this.writableDatabase

        val valores = ContentValues().apply {
            put("latitude", latitude)
            put("longitude", longitude)
        }
        return bd.update("Sensor", valores, "idsensor=?", arrayOf(id.toString()))
    }

    fun deletarSensor(id: Int): Int {
        val bd = this.writableDatabase
        return bd.delete("Sensor", "idsensor=?", arrayOf(id.toString()))
    }

    fun obterSensores(): List<Sensor> {
        val bd = this.readableDatabase
        val cursor = bd.rawQuery("SELECT * FROM Sensor", null)
        val sensores = mutableListOf<Sensor>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("idsensor"))
                val latitude = cursor.getString(cursor.getColumnIndexOrThrow("latitude"))
                val longitude = cursor.getString(cursor.getColumnIndexOrThrow("longitude"))
                sensores.add(Sensor(id, latitude, longitude))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return sensores
    }

    data class Sensor(
        val id: Int,
        var latitude: String,
        var longitude: String
    )


    fun obterAlertas(): List<Alerta> {
        val bd = this.readableDatabase
        val cursor = bd.rawQuery("SELECT * FROM Alerta", null)
        val alertas = mutableListOf<Alerta>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("idalerta"))
                val fk_idsensor = cursor.getInt(cursor.getColumnIndexOrThrow("fk_idsensor"))
                val datahora = cursor.getString(cursor.getColumnIndexOrThrow("datahora"))
                val temperatura = cursor.getInt(cursor.getColumnIndexOrThrow("temperatura"))
                val umidade = cursor.getInt(cursor.getColumnIndexOrThrow("umidade"))
                val fogo = cursor.getInt(cursor.getColumnIndexOrThrow("fogo"))

            }while(cursor.moveToNext())

        }
        cursor.close()
        return alertas
    }
    data class Alerta(
        val id: Int,
        var fk_idsensor: Int,
        val datahora: String,
        val temperatura: Int,
        val umidade: Int,
        val fogo: Int)
    fun autenticarUsuario(login: String, senha: String): Boolean {
        val bd = this.writableDatabase

        val cursor = bd.rawQuery(
            "SELECT * FROM Bombeiro WHERE login = ? AND senha = ?",
            arrayOf(login, senha)
        )


        val usuarioExiste = cursor.count > 0



        cursor.close()

        return usuarioExiste


    }
}