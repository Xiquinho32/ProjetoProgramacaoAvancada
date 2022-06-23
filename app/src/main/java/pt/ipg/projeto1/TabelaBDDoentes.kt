package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDDoentes(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME_DOENTE TEXT NOT NULL, $CAMPO_CC TEXT NOT NULL, $CAMPO_DATA_NASCIMENTO TEXT NOT NULL, $CAMPO_DOENCA_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_DOENCA_ID) REFERENCES ${TabelaBDDoencas.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }
    companion object{
        const val NOME = "Doentes"
        const val CAMPO_ID = "${TabelaBDDoentes.NOME}.${BaseColumns._ID}"
        const val CAMPO_NOME_DOENTE = "nome_doente"
        const val CAMPO_CC = "cartao_cidadao"
        const val CAMPO_DATA_NASCIMENTO = "data_nascimento"
        const val CAMPO_DOENCA_ID = "doenca"

    }
}