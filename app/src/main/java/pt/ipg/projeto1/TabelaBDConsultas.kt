package pt.ipg.projeto1

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDConsultas(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE IF NOT EXISTS $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,  $CAMPO_DATA TEXT NOT NULL, $CAMPO_MEDICO_ID INTEGER NOT NULL,$CAMPO_DOENTE_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_MEDICO_ID) REFERENCES ${TabelaBDMedicos.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT, FOREIGN KEY ($CAMPO_DOENTE_ID) REFERENCES ${TabelaBDDoentes.NOME}(${BaseColumns._ID})ON DELETE RESTRICT)")
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
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaBDMedicos.NOME} ON ${TabelaBDMedicos.CAMPO_ID} = $CAMPO_MEDICO_ID"; "$NOME INNER JOIN ${TabelaBDDoentes.NOME} ON ${TabelaBDDoentes.CAMPO_ID} = $CAMPO_DOENTE_ID"
        //queryBuilder.tables= "$NOME INNER JOIN ${TabelaBDDoentes.NOME} ON ${TabelaBDDoentes.CAMPO_ID} = $CAMPO_DOENTE_ID"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }
    companion object{
        const val NOME = "Consultas"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_DATA = "data"
        const val CAMPO_DOENTE_ID = "doenteId"
        const val CAMPO_MEDICO_ID = "medicoId"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_DATA, CAMPO_DOENTE_ID, CAMPO_MEDICO_ID, TabelaBDMedicos.CAMPO_NOME_MEDICO, TabelaBDDoentes.CAMPO_NOME_DOENTE)
    }
}