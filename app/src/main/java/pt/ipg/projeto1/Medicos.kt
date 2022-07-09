package pt.ipg.projeto1

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Medicos(
    var nome: String = "",
    var cc : String = "",
    var idEspecialidades: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues{
        val valores =ContentValues()

        valores.put(TabelaBDMedicos.CAMPO_NOME_MEDICO, nome)
        valores.put(TabelaBDMedicos.CAMPO_CC, cc)
        valores.put(TabelaBDMedicos.CAMPO_ESPECIALIDADES_ID, idEspecialidades)
        valores.put(TabelaBDMedicos.CAMPO_ID, id)

        return valores

    }
    companion object{
        fun fromCursor(cursor: Cursor):Medicos{
            val posId =cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_NOME_MEDICO)
            val posCC = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_CC)
            val posIdEspecialidades = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_ESPECIALIDADES_ID)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val cc = cursor.getString(posCC)
            val idEspecialidades= cursor.getLong(posIdEspecialidades)

            return Medicos(nome, cc, idEspecialidades, id)
        }
    }


}