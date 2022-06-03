package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDMedicos(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE ${TabelaBDMedicos.Campo_ID_medico} (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${TabelaBDMedicos.Campo_CC} LONG NOT NULL, ${TabelaBDMedicos.Campo_Especialidades} STRING NOT NULL)")  }

    companion object{
        const val NOME = "Médicos"
        const val Campo_ID_medico = "id_medico"
        const val Campo_CC = "cartão_cidadão"
        const val Campo_Especialidades = "especialidades"
    }
}