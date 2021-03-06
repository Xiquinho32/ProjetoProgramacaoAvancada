package pt.ipg.projeto1


import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDMedicos(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE IF NOT EXISTS $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME_MEDICO TEXT NOT NULL, $CAMPO_CC TEXT NOT NULL, $CAMPO_ESPECIALIDADES_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ESPECIALIDADES_ID) REFERENCES ${TabelaBDEspecialidades.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
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
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaBDEspecialidades.NOME} ON ${TabelaBDEspecialidades.CAMPO_ID} = $CAMPO_ESPECIALIDADES_ID"


        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME = "Medicos"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_NOME_MEDICO = "nome_medico"
        const val CAMPO_CC = "cartao_cidadao"
        const val CAMPO_ESPECIALIDADES_ID = "especialidadesId"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME_MEDICO, CAMPO_CC, CAMPO_ESPECIALIDADES_ID, TabelaBDEspecialidades.CAMPO_TIPO_ESPECIALIDADES)

    }

}