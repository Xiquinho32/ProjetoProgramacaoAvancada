package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.format.DateTimeFormatter

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
        doencas.id = TabelaBDDoencas(db).insert(doencas.ToContentValues())
        assertNotEquals(-1, doencas.id)
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
        val consultas = Consultas("25 de junho de 2022", 4, 4, 6)
        insereConsultas(db, consultas)

        db.close()
    }
}

