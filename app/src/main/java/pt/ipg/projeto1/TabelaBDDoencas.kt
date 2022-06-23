package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDDoencas(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGR PRIMARY KEY AUTOINCREMENT, $CAMPO_TIPO_DOENCAS TEXT NOT NULL)")
    }
    companion object{
        const val NOME = "Doencas"
        const val CAMPO_ID = "${TabelaBDDoencas.NOME}.${BaseColumns._ID}"
        const val CAMPO_TIPO_DOENCAS ="tipo_doenca"
    }
}