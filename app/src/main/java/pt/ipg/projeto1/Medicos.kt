package pt.ipg.projeto1

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Medicos(
    var nome: String,
    var cc: String,
    var especialidades: Especialidades,
    var id: Long = -1
) : Serializable {
    fun toContentValues() : ContentValues{
        val valores =ContentValues()

        valores.put(TabelaBDMedicos.CAMPO_NOME_MEDICO, nome)
        valores.put(TabelaBDMedicos.CAMPO_CC, cc)
        valores.put(TabelaBDMedicos.CAMPO_ESPECIALIDADES_ID, especialidades.id)
        //valores.put(TabelaBDMedicos.CAMPO_ID, id)

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor):Medicos{
            val posId =cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_NOME_MEDICO)
            val posCC = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_CC)
            val posIdEspecialidades = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_ESPECIALIDADES_ID)
            val posTipoEspecialidades = cursor.getColumnIndex(TabelaBDEspecialidades.CAMPO_TIPO_ESPECIALIDADES)
           // val pos2IdEspecialidades = cursor.getColumnIndex(TabelaBDEspecialidades.CAMPO_ID)

            //medicos
            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val cc = cursor.getString(posCC)
            //val especialidadesMedicos= cursor.getLong(posIdEspecialidades)

            // especialidades
            val idEspecialidades = cursor.getLong(posIdEspecialidades)
            val tipoEspecialidades = cursor.getString(posTipoEspecialidades)

            val especialidades = Especialidades(tipoEspecialidades, idEspecialidades)

            return Medicos(nome, cc, especialidades, id)
        }
    }


}