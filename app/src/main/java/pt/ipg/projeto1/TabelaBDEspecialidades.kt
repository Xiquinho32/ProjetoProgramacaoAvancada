package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDEspecialidades(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_TIPO_ESPECIALIDADES TEXT NOT NULL)")
    }
    companion object{
        const val NOME = "Especialidades"
        const val CAMPO_ID = "${TabelaBDEspecialidades.NOME}.${BaseColumns._ID}"
        const val CAMPO_TIPO_ESPECIALIDADES = "tipo_especialidades"
    }
}