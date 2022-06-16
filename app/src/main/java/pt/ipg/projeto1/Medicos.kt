package pt.ipg.projeto1

import android.content.ContentValues

data class Medicos(
    var Nome: String,
    var CC : String,
    var Especialidades: String,
    var id: Long =  -1
) {
    fun toContentValues() : ContentValues{
        val valores =ContentValues()

        valores.put(TabelaBDMedicos.Campo_Nome_Medico, Nome)
        valores.put(TabelaBDMedicos.Campo_CC, CC)
        valores.put(TabelaBDMedicos.Campo_Especialidades, Especialidades)

        return valores

    }


}