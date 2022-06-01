package pt.ipg.projeto1

import android.content.ContentValues
import java.sql.Date


data class Doentes(
    var ID_Doente : Long,
    var NOME : String,
    var Data_Nascimento : Date, //java.sql
    var Tipo_Doenca : String
){
    /*
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        return valores
    }
*/
}