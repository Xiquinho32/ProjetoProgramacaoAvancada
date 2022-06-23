package pt.ipg.projeto1

import android.content.ContentValues

data class Especialidades(
    var nome : String,
    var tipoEspecialidades : String,
    var id : Long = 1
) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDEspecialidades.CAMPO_TIPO_ESPECIALIDADES, tipoEspecialidades)

        return valores
    }
}