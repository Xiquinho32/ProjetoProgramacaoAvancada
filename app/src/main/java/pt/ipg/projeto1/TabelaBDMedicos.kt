package pt.ipg.projeto1


import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDMedicos(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE IF NOT EXISTS $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME_MEDICO TEXT NOT NULL, $CAMPO_CC TEXT NOT NULL, $CAMPO_ESPECIALIDADES_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ESPECIALIDADES_ID) REFERENCES ${TabelaBDEspecialidades.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object{
        const val NOME = "Medicos"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_NOME_MEDICO = "nome_medico"
        const val CAMPO_CC = "cartao_cidadao"
        const val CAMPO_ESPECIALIDADES_ID = "especialidadesId"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME_MEDICO, CAMPO_CC, CAMPO_ESPECIALIDADES_ID)

    }

}