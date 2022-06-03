package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDDoentes(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $Campo_Nome_Doente TEXT NOT NULL, $Campo_CC STRING NOT NULL, $Campo_Data_Nascimento DATE NOT NULL, $Campo_Tipo_Doenca)")
    }
    companion object{
        const val NOME = "Doentes"
        const val Campo_Nome_Doente = "nome_doente"
        const val Campo_CC = "cartao_cidadao"
        const val Campo_Data_Nascimento = "data_nascimento"
        const val Campo_Tipo_Doenca = "tipo_doenca"
    }
}