package pt.ipg.projeto1

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable


data class Doentes(
    var nome: String = "",
    var cc: String = "",
    var dataNascimento: String = "", //java.util
    var doencas: Doencas,
    var id: Long =  -1
): Serializable{


    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDDoentes.CAMPO_NOME_DOENTE, nome)
        valores.put(TabelaBDDoentes.CAMPO_CC, cc)
        valores.put(TabelaBDDoentes.CAMPO_DATA_NASCIMENTO, dataNascimento)
        valores.put(TabelaBDDoentes.CAMPO_DOENCA_ID, doencas.id)
        //valores.put(TabelaBDDoentes.CAMPO_ID, id)
        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor):Doentes{
            val posId =cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDDoentes.CAMPO_NOME_DOENTE)
            val posDataNascimento = cursor.getColumnIndex(TabelaBDDoentes.CAMPO_DATA_NASCIMENTO)
            val posCC = cursor.getColumnIndex(TabelaBDDoentes.CAMPO_CC)
            val posIdDoencas = cursor.getColumnIndex(TabelaBDDoentes.CAMPO_DOENCA_ID)
            val posTipoDoencas = cursor.getColumnIndex(TabelaBDDoencas.CAMPO_TIPO_DOENCAS)
            //val pos2IdDoencas = cursor.getColumnIndex(TabelaBDDoencas.CAMPO_ID)

            //doentes
            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val dataNascimento = cursor.getString(posDataNascimento)
            val cc = cursor.getString(posCC)
            //val doencasDoentes = cursor.getLong(posIdDoencas)

            //doencas
            val idDoencas = cursor.getLong(posIdDoencas)
            val tipoDoencas = cursor.getString(posTipoDoencas)


            val doencas = Doencas(tipoDoencas, idDoencas)

            return Doentes(nome, cc, dataNascimento, doencas, id)
        }
    }

}