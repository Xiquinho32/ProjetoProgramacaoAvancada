package pt.ipg.projeto1

import android.content.ContentValues
import java.sql.Date


data class Doentes(
    var nome : String,
    var cc : String,
    var dataNascimento : String, //java.util
    var idDoencas: Long,
    var id: Long =  1
){


    fun toContentValues() : ContentValues{
        val valores = ContentValues()
        valores.put(TabelaBDDoentes.CAMPO_NOME_DOENTE, nome)
        valores.put(TabelaBDDoentes.CAMPO_CC, cc)
        valores.put(TabelaBDDoentes.CAMPO_DATA_NASCIMENTO, dataNascimento)
        valores.put(TabelaBDDoentes.CAMPO_DOENCA_ID, idDoencas)
        valores.put(TabelaBDDoentes.CAMPO_ID, id)
        return valores
    }

}