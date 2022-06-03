package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDMedicos(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE ${TabelaBDMedicos.NOME} (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $Campo_Nome_Medico STRING NOT NULL, $Campo_CC STRING NOT NULL, $Campo_Especialidades STRING NOT NULL)")  }

    companion object{
        const val NOME = "Medicos"
        const val Campo_Nome_Medico = "nome_medico"
        const val Campo_CC = "cartao_cidadao"
        const val Campo_Especialidades = "especialidades"
    }
}