package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
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
        assertNotEquals(-1, doentes.id)
    }

    private fun insereMedicos(db: SQLiteDatabase, medicos: Medicos) {
        medicos.id = TabelaBDMedicos(db).insert(medicos.toContentValues())
        assertNotEquals(-1, medicos.id)
    }

    private fun insereConsultas(db: SQLiteDatabase, consultas: Consultas) {
        consultas.id = TabelaBDConsultas(db).insert(consultas.toContentValues())
        assertNotEquals(-1, consultas.id)
    }

    private fun insereDoencas(db: SQLiteDatabase, doencas: Doencas) {
        doencas.id = TabelaBDDoencas(db).insert(doencas.toContentValues())
        assertNotEquals(-1, doencas.id)
    }

    private fun insereEspecialidades(db: SQLiteDatabase, especialidades: Especialidades) {
        especialidades.id = TabelaBDEspecialidades(db).insert(especialidades.toContentValues())
        assertNotEquals(-1, especialidades.id)

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
    fun consegueInserirEspecialidades() {
        val db = getWritableDatabase()

        val especialidades = Especialidades("Geral")
        insereEspecialidades(db, especialidades)
        db.close()
    }


    @Test
    fun consegueInserirMedicos() {
        val db = getWritableDatabase()

        val especialidades = Especialidades("Geral")
        insereEspecialidades(db, especialidades)

        val medicos = Medicos("João", "123455", especialidades)
        insereMedicos(db, medicos)

        db.close()
    }
    @Test
    fun consegueInserirDoencas() {
        val db = getWritableDatabase()

        val doencas = Doencas("Dor de peito")
        insereDoencas(db, doencas)
        db.close()
    }


    @Test
    fun consegueInserirDoentes() {
        val db = getWritableDatabase()

        val doencas = Doencas("Dor de peito")
        insereDoencas(db, doencas)

        val doentes = Doentes("Josefino",  "3 de abril de 1984", doencas)
        insereDoentes(db, doentes)


        //val teste = Doentes()
        //val doencas = Doencas("Dor de peito", 1)
        db.close()
    }

    @Test
    fun consegueInserirConsultas() {
        val db = getWritableDatabase()

        val tipoEspecialidades = Especialidades("Geral")
        insereEspecialidades(db, tipoEspecialidades)

        val medicos = Medicos("João", "123455", tipoEspecialidades)
        insereMedicos(db, medicos)

        val doencas = Doencas("dor de peito")
        insereDoencas(db, doencas)


        val doentes = Doentes("Josefino", "3 de abril de 1984", doencas)
        insereDoentes(db, doentes)
        val consultas = Consultas("25 de junho de 2022", medicos, doentes)
        insereConsultas(db, consultas)

        db.close()

    }


    @Test
    fun consegueAlterarEspecialidades() {
        val db = getWritableDatabase()

        val especialidades = Especialidades("Teste")
        insereEspecialidades(db, especialidades)

        especialidades.tipoEspecialidades = "Acupuntura"

        val registosAlterados = TabelaBDEspecialidades(db).update(
            especialidades.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${especialidades.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarMedicos() {
        val db = getWritableDatabase()

        val especialidadesGeral = Especialidades("Geral")
        insereEspecialidades(db, especialidadesGeral)

        val especialidadesAcupuntura = Especialidades("Acupuntura")
        insereEspecialidades(db, especialidadesAcupuntura)

        val medicos = Medicos("Teste", "Teste", especialidadesGeral)
        insereMedicos(db, medicos)

        medicos.nome = "Josefino"
        medicos.cc = "6654321"
        medicos.especialidades = especialidadesAcupuntura

        val registosAlterados = TabelaBDMedicos(db).update(
            medicos.toContentValues(),
            "${TabelaBDMedicos.CAMPO_ID}= ?",
            arrayOf("${medicos.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()
    }
    @Test
    fun consegueAlterarDoencas() {
        val db = getWritableDatabase()

        val doencas = Doencas("Teste")
        insereDoencas(db, doencas)

        doencas.tipoDoenca = "Dor de peito"

        val registosAlterados = TabelaBDDoencas(db).update(
            doencas.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${doencas.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()
    }
    @Test
    fun consegueAlterarDoentes() {
        val db = getWritableDatabase()

        val doencasDorDeBraco = Doencas("Dor de braço")
        insereDoencas(db, doencasDorDeBraco)

        val doencasDorDeCabeca = Doencas("Dor de cabeça")
        insereDoencas(db, doencasDorDeCabeca)

        val doente = Doentes("Teste",  "Teste", doencasDorDeCabeca)
        insereDoentes(db, doente)
        doente.nome = "Francisco"
        doente.dataNascimento = "26/11/2000"
        doente.doencas = doencasDorDeCabeca

        val registosAlterados = TabelaBDDoentes(db).update(
            doente.toContentValues(),
            "${TabelaBDDoentes.CAMPO_ID}=?",
            arrayOf("${doente.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()


    }

    @Test
    fun consegueAlterarConsultas() {
        val db = getWritableDatabase()

        val especialidades = Especialidades("Geral")
        insereEspecialidades(db, especialidades)

        val medicosNome = Medicos("João", "123455", especialidades)
        insereMedicos(db, medicosNome)

        val doencas = Doencas("Dor de braço")
        insereDoencas(db, doencas)

        val doentes = Doentes("Josefino",  "3 de abril de 1984", doencas)
        insereDoentes(db, doentes)


        val consultas = Consultas("Teste", medicosNome, doentes)
        insereConsultas(db, consultas)


        consultas.data = "22 de janeiro de 2021"


        val registosAlterados = TabelaBDConsultas(db).update(
            consultas.toContentValues(),
            "${TabelaBDConsultas.CAMPO_ID}= ?",
            arrayOf("${consultas.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }

    @Test
    fun consegueEliminarEspecialidade() {
        val db = getWritableDatabase()

        val especialidade = Especialidades("geral")
        insereEspecialidades(db, especialidade)


        val registosEliminados = TabelaBDEspecialidades(db).delete(
            "${TabelaBDEspecialidades.CAMPO_ID}=?",
            arrayOf("${especialidade.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarDoenca() {
        val db = getWritableDatabase()

        val doenca = Doencas("dor de cabelo")
        insereDoencas(db, doenca)


        val registosEliminados = TabelaBDDoencas(db).delete(
            "${TabelaBDDoencas.CAMPO_ID}=?",
            arrayOf("${doenca.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }
    @Test
    fun consegueEliminarDoentes() {
        val db = getWritableDatabase()

        val doencas = Doencas("Dor de braço")
        insereDoencas(db, doencas)

        val doente = Doentes("Alberto",  "23 de janeiro de 2021", doencas)
        insereDoentes(db, doente)

        val registosEliminados = TabelaBDDoentes(db).delete(
            "${TabelaBDDoentes.CAMPO_ID}=?",
            arrayOf("${doente.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()

    }

    @Test
    fun consegueEliminarMedicos() {
        val db = getWritableDatabase()

        val especialidades = Especialidades("Geral")
        insereEspecialidades(db, especialidades)

        val medicos = Medicos("João", "123455", especialidades)
        insereMedicos(db, medicos)

        val registosEliminados = TabelaBDMedicos(db).delete(
            "${TabelaBDMedicos.CAMPO_ID}=?",
            arrayOf("${medicos.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()

    }

    @Test
    fun consegueEliminarConsultas() {
        val db = getWritableDatabase()

        val especialidades = Especialidades("Geral")
        insereEspecialidades(db, especialidades)

        val medicosNome = Medicos("João", "123455", especialidades)
        insereMedicos(db, medicosNome)

        val doencas = Doencas("Dor de braço")
        insereDoencas(db, doencas)

        val doentes = Doentes("Josefino",  "3 de abril de 1984", doencas)
        insereDoentes(db, doentes)


        val consultas = Consultas("Teste", medicosNome, doentes)
        insereConsultas(db, consultas)

        val registosAlterados = TabelaBDConsultas(db).delete(
            "${TabelaBDConsultas.CAMPO_ID}= ?",
            arrayOf("${consultas.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }

    @Test
    fun consegueLerEspecialidades() {
        val db = getWritableDatabase()

        val especialidades = Especialidades("geral")
        insereEspecialidades(db, especialidades)


        val cursor = TabelaBDEspecialidades(db).query(
            TabelaBDEspecialidades.TODAS_COLUNAS,
            "${TabelaBDEspecialidades.CAMPO_ID}=?",
            arrayOf("${especialidades.id}"),
            null,
            null,
            null
        )
        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val espBD = Especialidades.fromCursor(cursor)  //consultas que tenho na BD
        assertEquals(especialidades, espBD) //comparar as duas categorias para ver se sao iguais

        db.close()
    }
    @Test
    fun consegueLerDoencas() {
        val db = getWritableDatabase()

        val doencas = Doencas("Dor de cotovelo")
        insereDoencas(db, doencas)


        val cursor = TabelaBDDoencas(db).query(
            TabelaBDDoencas.TODAS_COLUNAS,
            "${TabelaBDDoencas.CAMPO_ID}=?",
            arrayOf("${doencas.id}"),
            null,
            null,
            null
        )
        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val doencasBD = Doencas.fromCursor(cursor)  //consultas que tenho na BD
        assertEquals(doencas, doencasBD) //comparar as duas categorias para ver se sao iguais

        db.close()
    }

    @Test
    fun consegueLerMedicos(){
        val db = getWritableDatabase()

        val especialidade = Especialidades("Geral")
        insereEspecialidades(db, especialidade)

        val medicos = Medicos("João","123455", especialidade)
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

        val medicoBD = Medicos.fromCursor(cursor)

        assertEquals(medicos, medicoBD) //comparar as duas categorias para ver se sao iguais

        db.close()
    }

    @Test
    fun consegueLerDoentes() {
        val db = getWritableDatabase()


        val doencasDorDeCabeca = Doencas("Dor de cabeça")
        insereDoencas(db, doencasDorDeCabeca)

        val doente = Doentes("Teste",  "Teste", doencasDorDeCabeca)
        insereDoentes(db, doente)


        val cursor = TabelaBDDoentes(db).query(
            TabelaBDDoentes.TODAS_COLUNAS,
            "${TabelaBDDoentes.CAMPO_ID}=?",
            arrayOf("${doente.id}"),
            null,
            null,
            null
        )


        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val doenteBD = Doentes.fromCursor(cursor)
        assertEquals(doente, doenteBD)

        db.close()


    }

    @Test
    fun consegueLerConsultas(){
        val db = getWritableDatabase()

        val especialidades = Especialidades("Geral")
        insereEspecialidades(db, especialidades)

        val medicos = Medicos("João","123455", especialidades)
        insereMedicos(db, medicos)

        val doencas = Doencas("Dor de braço")
        insereDoencas(db, doencas)

        val doentes = Doentes("Josefino",  "3 de abril de 1984",doencas)
        insereDoentes(db, doentes)

        val consultas = Consultas("25 de junho de 2022", medicos, doentes)
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


    }
/*

*/
