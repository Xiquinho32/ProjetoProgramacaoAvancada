package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDConsultas(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $Campo_Doente_ID INTEGER NOT NULL, FOREIGN KEY ($Campo_Doente_ID) REFERENCES ${TabelaBDDoentes.NOME}(${BaseColumns._ID})ON DELETE RESTRICT, $Campo_Medico_ID INTEGER NOT NULL, FOREIGN KEY($Campo_Medico_ID) REFERENCES ${TabelaBDMedicos.NOME}(${BaseColumns._ID} ON DELETE RESTRICT))")
    }
    companion object{
        const val NOME = "Consultas"
        const val Campo_Doente_ID = "doenteId"
        const val Campo_Medico_ID = "medicoId"
    }
}