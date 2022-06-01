package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDDoentes(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $Campo_ID_Doente (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $Campo_Nome_Doente TEXT NOT NULL, $Campo_CC INTEGER NOT NULL, $Campo_Data_Nascimento DATE NOT NULL, $Campo_Tipo_Doenca)")
    }
    companion object{
        const val NOME = "Doentes"
        const val Campo_ID_Doente = "id_doente"
        const val Campo_Nome_Doente = "nome_doente"
        const val Campo_CC = "cartão_cidadão"
        const val Campo_Data_Nascimento = "data_nascimento"
        const val Campo_Tipo_Doenca = "tipo_doença"
    }
}