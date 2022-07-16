package pt.ipg.projeto1

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Doencas(
    var tipoDoenca: String = "",
    var id: Long = -1
) : Serializable{
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDDoencas.CAMPO_TIPO_DOENCAS, tipoDoenca)

        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor): Doencas{
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDDoencas.CAMPO_TIPO_DOENCAS)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Doencas(nome, id)
        }
    }

}