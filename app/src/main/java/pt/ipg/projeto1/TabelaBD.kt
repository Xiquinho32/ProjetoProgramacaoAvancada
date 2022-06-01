package pt.ipg.projeto1

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

abstract class TabelaBD(val db: SQLiteDatabase, val nome: String) {
    abstract fun cria()

    fun insert(values: ContentValues) =
        db.insert(nome, null, values)


}