package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDConsultas(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,  $CAMPO_DATA TEXT NOT NULL,$CAMPO_DOENTE_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_DOENTE_ID) REFERENCES ${TabelaBDDoentes.NOME}(${BaseColumns._ID})ON DELETE RESTRICT, $CAMPO_MEDICO_ID INTEGER NOT NULL, FOREIGN KEY($CAMPO_MEDICO_ID) REFERENCES ${TabelaBDMedicos.NOME}(${BaseColumns._ID} ON DELETE RESTRICT))")
    }
    companion object{
        const val NOME = "Consultas"
        const val CAMPO_ID = "${TabelaBDConsultas.NOME}.${BaseColumns._ID}"
        const val CAMPO_DATA = "data"
        const val CAMPO_DOENTE_ID = "doenteId"
        const val CAMPO_MEDICO_ID = "medicoId"
    }
}