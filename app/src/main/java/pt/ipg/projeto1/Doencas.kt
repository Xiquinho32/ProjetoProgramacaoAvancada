package pt.ipg.projeto1

import android.content.ContentValues

data class Doencas(
    var tipoDoenca: String,
    var id: Long = 1
) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDDoencas.CAMPO_TIPO_DOENCAS, tipoDoenca)

        return valores
    }
}