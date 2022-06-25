package pt.ipg.projeto1

import android.content.ContentValues

data class Especialidades(
    var tipoEspecialidades : String,
    var id : Long = 1
) {

    fun ToContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDEspecialidades.CAMPO_TIPO_ESPECIALIDADES, tipoEspecialidades)
        //valores.put(TabelaBDEspecialidades.CAMPO_ID, id)

        return valores
    }
}