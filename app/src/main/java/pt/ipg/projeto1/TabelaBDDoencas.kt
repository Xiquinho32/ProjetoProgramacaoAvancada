package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDDoencas(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE IF NOT EXISTS $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_TIPO_DOENCAS TEXT NOT NULL)")
    }
    companion object{
        const val NOME = "Doencas"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_TIPO_DOENCAS ="tipo_doenca"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_TIPO_DOENCAS)

    }
}