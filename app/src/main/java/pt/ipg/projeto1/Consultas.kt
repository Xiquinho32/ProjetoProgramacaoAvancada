package pt.ipg.projeto1

import android.content.ContentValues
import java.util.*

data class Consultas(var nome: String,  var doente_id : Long = 1, var medico_id : Long = 1,var id: Long = 1){
    fun toContentValues() : ContentValues{
        val valores =ContentValues()

        valores.put(TabelaBDConsultas.NOME, nome)
        valores.put(TabelaBDConsultas.Campo_Doente_ID, doente_id)
        valores.put(TabelaBDConsultas.Campo_Medico_ID, medico_id)

        return valores;
    }
}
