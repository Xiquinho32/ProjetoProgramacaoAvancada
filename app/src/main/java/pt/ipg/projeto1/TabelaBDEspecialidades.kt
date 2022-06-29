package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDEspecialidades(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE IF NOT EXISTS $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_TIPO_ESPECIALIDADES TEXT NOT NULL)")
    }
    companion object{
        const val NOME = "Especialidades"
       const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_TIPO_ESPECIALIDADES = "tipo_especialidades"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_TIPO_ESPECIALIDADES)
    }
}