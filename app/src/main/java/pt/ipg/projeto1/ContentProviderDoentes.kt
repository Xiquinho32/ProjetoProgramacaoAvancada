package pt.ipg.projeto1

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderDoentes : ContentProvider(){
    var dbOpenHelper : BDDoentesOpenHelper? = null

    override fun onCreate(): Boolean{
        dbOpenHelper = BDDoentesOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? =
        when (getUriMatcher().match(uri)) {
            URI_DOENTES ->"$MULTIPLOS_REGISTOS/${TabelaBDDoentes.NOME}"
            URI_DOENTES_ESPECIFICA ->"$UNICO_REGISTO/${TabelaBDDoentes.NOME}"
            URI_MEDICOS ->"$MULTIPLOS_REGISTOS/${TabelaBDMedicos.NOME}"
            URI_MEDICOS_ESPECIFICA ->"$UNICO_REGISTO/${TabelaBDMedicos.NOME}"
            URI_CONSULTAS ->"$MULTIPLOS_REGISTOS/${TabelaBDConsultas.NOME}"
            URI_CONSULTAS_ESPECIFICA ->"$UNICO_REGISTO/${TabelaBDConsultas.NOME}"

            else -> null
        }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbOpenHelper!!.writableDatabase

        requireNotNull(values)

        val id = when (getUriMatcher().match(uri)){
            URI_DOENTES ->TabelaBDDoentes(db).insert(values)
            URI_CONSULTAS -> TabelaBDConsultas(db).insert(values)
            URI_MEDICOS -> TabelaBDMedicos(db).insert(values)
            else -> -1
        }
        db.close()

        if(id == -1L) return null //-1L , id é do tipo long

        return Uri.withAppendedPath(uri, "$id")

    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

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
