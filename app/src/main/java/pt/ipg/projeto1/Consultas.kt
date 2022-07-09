package pt.ipg.projeto1

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Consultas(
    var data: String,
    var medicos: Medicos,
    var doentes: Doentes,
    var id: Long = -1
) : Serializable{
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDConsultas.CAMPO_DATA, data)
        valores.put(TabelaBDConsultas.CAMPO_DOENTE_ID, doentes.id)
        valores.put(TabelaBDConsultas.CAMPO_MEDICO_ID, medicos.id)
        valores.put(TabelaBDConsultas.CAMPO_ID, id)

        return valores;
    }
    companion object{
        fun fromCursor(cursor: Cursor):Consultas{
            val posId =cursor.getColumnIndex(BaseColumns._ID)
            val posData = cursor.getColumnIndex(TabelaBDConsultas.CAMPO_DATA)
            val posIdMedicos = cursor.getColumnIndex(TabelaBDConsultas.CAMPO_MEDICO_ID)
            val posNomeMedicos = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_NOME_MEDICO)
            val posccMedicos = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_CC)
            val posIdDoentes = cursor.getColumnIndex(TabelaBDConsultas.CAMPO_DOENTE_ID)
            val posNomeDoentes = cursor.getColumnIndex(TabelaBDDoentes.CAMPO_NOME_DOENTE)
            val posccDoentes = cursor.getColumnIndex(TabelaBDDoentes.CAMPO_CC)
            val posDataNascimentoDoentes = cursor.getColumnIndex(TabelaBDDoentes.CAMPO_DATA_NASCIMENTO)

            val id = cursor.getLong(posId)
            val data = cursor.getString(posData)

            val idMedico = cursor.getLong(posIdMedicos)
            val nomeMedico = cursor.getString(posNomeMedicos)
            val ccMedicos = cursor.getString(posccMedicos)
            val medico = Medicos(nomeMedico, ccMedicos, idMedico)


            val idDoentes = cursor.getLong(posIdDoentes)
            val nomeDoentes = cursor.getString(posNomeDoentes)
            val ccDoentes = cursor.getString(posccDoentes)
            val dataNascimentoDoentes = cursor.getString(posDataNascimentoDoentes)
            val doente = Doentes(nomeDoentes, ccDoentes, dataNascimentoDoentes, idDoentes)

            return Consultas(data, medico,doente , id)
        }
    }
}
