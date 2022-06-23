package pt.ipg.projeto1

import android.content.ContentValues
import java.sql.Date


data class Doentes(
    var nome : String,
    var cc : String,
    var dataNascimento : String, //java.util
    var id: Long =  1
){


    fun toContentValues() : ContentValues{
        val valores = ContentValues()
        valores.put(TabelaBDDoentes.CAMPO_NOME_DOENTE, nome)
        valores.put(TabelaBDDoentes.CAMPO_CC, cc)
        valores.put(TabelaBDDoentes.CAMPO_DATA_NASCIMENTO, dataNascimento)
        //valores.get(TabelaBDDoentes.Campo_Data_Nascimento, Data_Nascimento)
        return valores
    }

}