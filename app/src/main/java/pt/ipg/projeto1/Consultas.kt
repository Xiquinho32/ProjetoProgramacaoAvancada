package pt.ipg.projeto1

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
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
    companion object{
        fun fromCursor(cursor: Cursor):Consultas{
            val posId =cursor.getColumnIndex(BaseColumns._ID)
            val posData = cursor.getColumnIndex(TabelaBDConsultas.CAMPO_DATA)
            val posIdMedicos = cursor.getColumnIndex(TabelaBDConsultas.CAMPO_MEDICO_ID)
            val posIdDoentes = cursor.getColumnIndex(TabelaBDConsultas.CAMPO_DOENTE_ID)

            val id = cursor.getLong(posId)
            val data = cursor.getString(posData)
            val idMedicos = cursor.getLong(posIdMedicos)
            val idDoentes = cursor.getLong(posIdDoentes)

            return Consultas(data, idMedicos, idDoentes, id)
        }
    }
}
