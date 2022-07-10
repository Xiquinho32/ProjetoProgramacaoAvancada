package pt.ipg.projeto1

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Especialidades(
    var tipoEspecialidades : String = "",
    var id : Long = -1
) :Serializable {
    fun ToContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDEspecialidades.CAMPO_TIPO_ESPECIALIDADES, tipoEspecialidades)

        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor): Especialidades{
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDEspecialidades.CAMPO_TIPO_ESPECIALIDADES)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Especialidades(nome, id)
        }
    }
}