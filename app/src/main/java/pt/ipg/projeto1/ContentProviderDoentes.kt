package pt.ipg.projeto1

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderDoentes {
    var dbOpenHelper : BDDoentesOpenHelper? = null

    companion object{
        const val AUTHORITY = "pt.ipg.livros"

        const val URI_CONSULTAS = 100
        const val URI_CONSULTAS_ESPECIFICA = 101

        const val URI_MEDICOS = 200
        const val URI_MEDICOS_ESPECIFICA = 201

        const val URI_DOENTES = 300
        const val URI_DOENTES_ESPECIFICA = 301


        const val UNICO_REGISTO = "vnd.android.cursor.item"
        const val MULTIPLOS_REGISTOS = "vnd.android.cursor.dir"
    }
    fun getUriMatcher() : UriMatcher {
        var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        uriMatcher.addURI(AUTHORITY, TabelaBDConsultas.NOME, URI_CONSULTAS)
        uriMatcher.addURI(AUTHORITY, "${TabelaBDConsultas.NOME}/#", URI_CONSULTAS_ESPECIFICA)

        uriMatcher.addURI(AUTHORITY, TabelaBDMedicos.NOME, URI_MEDICOS)
        uriMatcher.addURI(AUTHORITY, "${TabelaBDMedicos.NOME}/#", URI_MEDICOS_ESPECIFICA)

        uriMatcher.addURI(AUTHORITY, TabelaBDDoentes.NOME, URI_DOENTES)
        uriMatcher.addURI(AUTHORITY, "${TabelaBDDoentes.NOME}/#", URI_DOENTES_ESPECIFICA)

        return uriMatcher
    }
}
