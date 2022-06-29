package pt.ipg.projeto1

import android.content.ContentValues

data class Medicos(
    var nome: String,
    var cc : String,
    var idEspecialidades: Long,
    var id: Long =  1
) {
    fun toContentValues() : ContentValues{
        val valores =ContentValues()

        valores.put(TabelaBDMedicos.CAMPO_NOME_MEDICO, nome)
        valores.put(TabelaBDMedicos.CAMPO_CC, cc)
        valores.put(TabelaBDMedicos.CAMPO_ESPECIALIDADES_ID, idEspecialidades)
        valores.put(TabelaBDMedicos.CAMPO_ID, id)

        return valores

    }


}