package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaseDadosTest {
    private fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDDoentesOpenHelper(appContext())
        return openHelper.writableDatabase
    }

    private fun insereDoentes(db: SQLiteDatabase, doentes: Doentes) {
        doentes.id = TabelaBDDoentes(db).insert(doentes.toContentValues())
        assertNotEquals(1, doentes.id)
    }

    private fun insereMedicos(db: SQLiteDatabase, medicos: Medicos) {
        medicos.id = TabelaBDMedicos(db).insert(medicos.toContentValues())
        assertNotEquals(1, medicos.id)
    }

    private fun insereConsultas(db: SQLiteDatabase, consultas: Consultas) {
        consultas.id = TabelaBDConsultas(db).insert(consultas.toContentValues())
        assertNotEquals(1, consultas.id)
    }

    private fun insereDoencas(db: SQLiteDatabase, doencas: Doencas) {
        doencas.id = TabelaBDDoencas(db).insert(doencas.ToContentValues())
        assertNotEquals(1, doencas.id)
    }

    private fun insereEspecialidades(db: SQLiteDatabase, especialidades: Especialidades) {
        especialidades.id = TabelaBDEspecialidades(db).insert(especialidades.ToContentValues())
        assertNotEquals(1, especialidades.id)

    }

    @Before
    fun apagaBaseDados() {
        appContext().deleteDatabase(BDDoentesOpenHelper.NOME)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDDoentesOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

    @Test
    fun consegueInserirEspecialidades(){
        val db = getWritableDatabase()

        val especialidades = Especialidades("Cardiologia", 2)
        insereEspecialidades(db, especialidades)

        db.close()
    }

    @Test
    fun consegueInserirMedicos(){
        val db = getWritableDatabase()

        val medicos = Medicos("João","123455", 2, 4)
        insereMedicos(db, medicos)

        db.close()
    }

    @Test
    fun consegueInserirDoencas(){
        val db = getWritableDatabase()

        val doencas = Doencas("Dor de peito", 2)
        insereDoencas(db, doencas)
        db.close()
    }
    @Test
    fun consegueInserirDoentes(){
        val db = getWritableDatabase()
        val doentes = Doentes("Josefino", "543212", "3 de abril de 1984",2, 4)
        insereDoentes(db, doentes)

        //val doencas = Doencas("Dor de peito", 1)
        db.close()
    }
    @Test
    fun consegueInserirConsultas(){
        val db = getWritableDatabase()

        val medicos = Medicos("João","123455", 2, 4)
        val doentes = Doentes("Josefino", "543212", "3 de abril de 1984",2, 4)
        val consultas = Consultas("25 de junho de 2022",  medicos, doentes,1)
        insereConsultas(db, consultas)

        db.close()

        //erros de inserir consultas
    }

    @Test
    fun consegueAlterarDoentes(){
        val db = getWritableDatabase()

        val doente = Doentes("Alberto", "987654321", "23 de janeiro de 2021", 3, 5)

        doente.nome = "Francisco"

        val registosAlterados = TabelaBDDoentes(db).update(
            doente.toContentValues(),
            "${TabelaBDDoentes.CAMPO_ID}=?",
             arrayOf("${doente.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

        //erro no alterar doentes
    }
    @Test
    fun consegueAlterarMedicos(){
        val db = getWritableDatabase()

        val medicos = Medicos("João","123455", 2, 4)

        medicos.cc = "6654321"

        val registosAlterados = TabelaBDMedicos(db).update(
            medicos.toContentValues(),
            "${TabelaBDMedicos.CAMPO_ID}= ?",
            arrayOf("${medicos.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarConsultas(){
        val db = getWritableDatabase()

        val medicos = Medicos("João","123455", 2, 4)
        val doentes = Doentes("Josefino", "543212", "3 de abril de 1984",2, 4)

        val consultas = Consultas("25 de junho de 2022", medicos, doentes, 6)

        consultas.id = 2

        val registosAlterados = TabelaBDConsultas(db).update(
            consultas.toContentValues(),
            "${TabelaBDConsultas.CAMPO_ID}= 2",
            arrayOf("${consultas.id}"))

        assertEquals(1, registosAlterados)

        db.close()



    }
    @Test
    fun consegueEliminarConsultas(){
        val db = getWritableDatabase()

        val medicos = Medicos("João","123455", 2, 4)
        val doentes = Doentes("Josefino", "543212", "3 de abril de 1984",2, 4)

        val consultas = Consultas("25 de junho de 2022", medicos, doentes, 6)
        insereConsultas(db, consultas)

        val registosEliminados = TabelaBDConsultas(db).delete(
            "${TabelaBDConsultas.CAMPO_ID}=?",
            arrayOf("${consultas.id}"))

        assertEquals(1, registosEliminados)

        db.close()

    }
    @Test
    fun consegueEliminarDoentes(){
        val db = getWritableDatabase()

        val doente = Doentes("Alberto", "987654321", "23 de janeiro de 2021", 3, 5)
        insereDoentes(db, doente)

        val registosEliminados = TabelaBDDoentes(db).delete(
            "${TabelaBDDoentes.CAMPO_ID}=?",
            arrayOf("${doente.id}"))

        assertEquals(1, registosEliminados)

        db.close()

    }
    @Test
    fun consegueEliminarMedicos(){
        val db = getWritableDatabase()

        val medicos = Medicos("João","123455", 2, 4)
        insereMedicos(db, medicos)

        val registosEliminados = TabelaBDMedicos(db).delete(
            "${TabelaBDMedicos.CAMPO_ID}=?",
            arrayOf("${medicos.id}"))

        assertEquals(1, registosEliminados)

        db.close()

    }
    @Test
    fun consegueLerConsultas(){
        val db = getWritableDatabase()

        val medicos = Medicos("João","123455", 2, 4)
        val doentes = Doentes("Josefino", "543212", "3 de abril de 1984",2, 4)

        val consultas = Consultas("25 de junho de 2022", medicos, doentes, 6)
        insereConsultas(db, consultas)

        val cursor = TabelaBDConsultas(db).query(
            TabelaBDConsultas.TODAS_COLUNAS,
            "${TabelaBDConsultas.CAMPO_ID}=?",
            arrayOf("${consultas.id}"),
            null,
            null,
            null
        )
        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val consultasBD = Consultas.fromCursor(cursor)  //consultas que tenho na BD
        assertEquals(consultas, consultasBD) //comparar as duas categorias para ver se sao iguais

        db.close()
    }

    @Test
    fun consegueLerMedicos(){
        val db = getWritableDatabase()

        val medicos = Medicos("João","123455", 2, 4)
        insereMedicos(db, medicos)

        val cursor = TabelaBDMedicos(db).query(
            TabelaBDMedicos.TODAS_COLUNAS,
            "${TabelaBDMedicos.CAMPO_ID}=?",
            arrayOf("${medicos.id}"),
            null,
            null,
            null
        )
        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val medicosBD = Consultas.fromCursor(cursor)  //consultas que tenho na BD
        assertEquals(medicos, medicosBD) //comparar as duas categorias para ver se sao iguais

        db.close()
    }
    @Test
    fun consegueLerDoentes(){
        val db = getWritableDatabase()

        val doentes = Doentes("Alberto", "987654321", "23 de janeiro de 2021", 3, 5)
        insereDoentes(db, doentes)

        val cursor = TabelaBDDoentes(db).query(
            TabelaBDDoentes.TODAS_COLUNAS,
            "${TabelaBDDoentes.CAMPO_ID}=?",
            arrayOf("${doentes.id}"),
            null,
            null,
            null
        )
        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val doentesBD = Consultas.fromCursor(cursor)  //consultas que tenho na BD
        assertEquals(doentes, doentesBD) //comparar as duas categorias para ver se sao iguais

        db.close()
    }
}

