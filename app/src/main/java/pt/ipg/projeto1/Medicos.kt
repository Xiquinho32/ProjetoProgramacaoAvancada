package pt.ipg.projeto1

import android.content.ContentValues

data class Medicos(
    var nome: String,
    var cc : String,
    var id: Long =  1
) {
    fun toContentValues() : ContentValues{
        val valores =ContentValues()

        valores.put(TabelaBDMedicos.Campo_Nome_Medico, nome)
        valores.put(TabelaBDMedicos.Campo_CC, cc)

        return valores

    }


}