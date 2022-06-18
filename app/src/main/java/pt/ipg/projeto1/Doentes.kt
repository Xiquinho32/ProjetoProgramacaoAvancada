package pt.ipg.projeto1

import android.content.ContentValues
import java.sql.Date


data class Doentes(
    var NOME : String,
    var CC : String,
    var Data_Nascimento : String, //java.util
    var Tipo_Doenca : String,
    var id: Long =  1
){


    fun toContentValues() : ContentValues{
        val valores = ContentValues()
        valores.put(TabelaBDDoentes.Campo_Nome_Doente, NOME)
        valores.put(TabelaBDDoentes.Campo_CC, CC)
        valores.put(TabelaBDDoentes.Campo_Data_Nascimento, Data_Nascimento)
        //valores.get(TabelaBDDoentes.Campo_Data_Nascimento, Data_Nascimento)
        valores.put(TabelaBDDoentes.Campo_Tipo_Doenca, Tipo_Doenca)
        return valores
    }

}