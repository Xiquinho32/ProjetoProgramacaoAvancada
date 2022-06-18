package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDEspecialidades(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $Campo_Acupuntura TEXT NOT NULL, $Campo_Cirugia_Geral TEXT NOT NULL, $Campo_Anestesiologia TEXT NOT NULL, $Campo_Cardiologia TEXT NOT NULL)")
    }
    companion object{
        const val NOME = "Especialidades"
        const val Campo_Acupuntura ="Acupuntura"
        const val Campo_Cardiologia = "Cardiologia"
        const val Campo_Cirugia_Geral = "Cirugia_Geral"
        const val Campo_Anestesiologia ="Anestesiologia"
    }
}