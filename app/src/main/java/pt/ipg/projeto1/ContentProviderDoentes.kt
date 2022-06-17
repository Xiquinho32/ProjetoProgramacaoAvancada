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
        val db = dbOpenHelper!!.readableDatabase

        requireNotNull(projection)
        val colunas = projection as Array<String>

        val argsSeleccao = selectionArgs as Array<String>

        val id = uri.lastPathSegment

        val cursor = when (getUriMatcher().match(uri)) {
            URI_DOENTES -> TabelaBDDoentes(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_MEDICOS -> TabelaBDDoentes(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_CONSULTAS -> TabelaBDDoentes(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_DOENTES_ESPECIFICA -> TabelaBDDoentes(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_MEDICOS_ESPECIFICA -> TabelaBDMedicos(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_CONSULTAS_ESPECIFICA -> TabelaBDConsultas(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            else -> null
        }

        db.close()

        return cursor
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

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        val registosApagados = when (getUriMatcher().match(uri)) {
            URI_DOENTES_ESPECIFICA -> TabelaBDDoentes(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_MEDICOS_ESPECIFICA -> TabelaBDMedicos(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_CONSULTAS_ESPECIFICA -> TabelaBDConsultas(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            else -> 0
        }

        db.close()

        return registosApagados
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        requireNotNull(values)

        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        val registosAlterados = when (getUriMatcher().match(uri)) {
            URI_DOENTES_ESPECIFICA -> TabelaBDDoentes(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_CONSULTAS_ESPECIFICA -> TabelaBDConsultas(db).update(values,"${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_MEDICOS_ESPECIFICA -> TabelaBDMedicos(db).update(values,"${BaseColumns._ID}=?", arrayOf("${id}"))
            else -> 0
        }

        db.close()

        return registosAlterados
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
