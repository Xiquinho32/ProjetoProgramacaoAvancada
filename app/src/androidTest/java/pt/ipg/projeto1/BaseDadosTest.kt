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

    private fun getWritableDatabase() :SQLiteDatabase{
        val openHelper = BDDoentesOpenHelper(appContext())
        return openHelper.writableDatabase
    }

    private fun insereDoentes(db: SQLiteDatabase, doentes: Doentes){
        doentes.id= TabelaBDDoentes(db).insert(doentes.toContentValues())
        assertNotEquals(-1, doentes.id)
    }

    private fun insereMedicos(db: SQLiteDatabase, medicos: Medicos){
        medicos.id = TabelaBDMedicos(db).insert(medicos.toContentValues())
        assertNotEquals(-1, medicos.id)
    }
    private fun insereConsultas(db: SQLiteDatabase, consultas: Consultas){
        consultas.id = TabelaBDConsultas(db).insert(consultas.toContentValues())
        assertNotEquals(-1, consultas.id)
    }

    @Before
    fun apagaBaseDados(){
        appContext().deleteDatabase(BDDoentesOpenHelper.NOME)
    }

    @Test
    fun ConsegueAbrirBaseDados(){
        val openHelper = BDDoentesOpenHelper(appContext())
        val db =openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

    @Test
    fun consegueInserirMedicos(){
        val db = getWritableDatabase()

        val consultas = Consultas("Verificação ao doente Afonso", doente_id = 1, medico_id = 1)
        insereConsultas(db, consultas)

        val medico = Medicos("Josefino", "123456789", "Cardiologia", consultas.id)
        insereMedicos(db, medico)

        db.close()
    }

    @Test
    fun consegueInserirDoentes(){
        val db = getWritableDatabase()


        val consultas = Consultas("Verificação ao doente Afonso", 1, 1)
        insereConsultas(db, consultas)


        val doente = Doentes("Afonso", "987654321", "3 de novembro de 2021", "Dor de peito",consultas.id)
        insereDoentes(db, doente)

        db.close()
    }

    @Test
    fun consgueInserirConsultas(){
        val db = getWritableDatabase()

        insereConsultas(db, Consultas("Verificação ao doente Afonso", doente_id = 1, medico_id = 1))
        db.close()
    }


}