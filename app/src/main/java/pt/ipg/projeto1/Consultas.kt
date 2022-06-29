package pt.ipg.projeto1

import android.content.ContentValues
import java.util.*

data class Consultas(
    var data: String,
    var idDoente : Long,
    var idMedico : Long,
    var id: Long = 1
){
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDConsultas.CAMPO_DATA, data)
        valores.put(TabelaBDConsultas.CAMPO_DOENTE_ID, idDoente)
        valores.put(TabelaBDConsultas.CAMPO_MEDICO_ID, idMedico)
        valores.put(TabelaBDConsultas.CAMPO_ID, id)

        return valores;
    }
}
