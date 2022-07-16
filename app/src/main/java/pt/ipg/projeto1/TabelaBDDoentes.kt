package pt.ipg.projeto1

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDDoentes(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE IF NOT EXISTS $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME_DOENTE TEXT NOT NULL, $CAMPO_CC TEXT NOT NULL, $CAMPO_DATA_NASCIMENTO TEXT NOT NULL, $CAMPO_DOENCA_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_DOENCA_ID) REFERENCES ${TabelaBDDoencas.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }
    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = "${TabelaBDDoencas.NOME} INNER JOIN ${TabelaBDDoencas.NOME} ON ${TabelaBDDoencas.CAMPO_ID} = $CAMPO_DOENCA_ID"


        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }
    companion object{
        const val NOME = "Doentes"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_NOME_DOENTE = "nome_doente"
        const val CAMPO_CC = "cartao_cidadao"
        const val CAMPO_DATA_NASCIMENTO = "data_nascimento"
        const val CAMPO_DOENCA_ID = "doencaId"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME_DOENTE, CAMPO_CC, CAMPO_DATA_NASCIMENTO, CAMPO_DOENCA_ID)
    }
}