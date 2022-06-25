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
    private fun insereDoencas(db: SQLiteDatabase, doencas: Doencas){
        doencas.id = TabelaBDDoencas(db).insert(doencas.ToContentValues())
        assertNotEquals(-1, doencas.id)
    }
    private fun insereEspecialidades(db: SQLiteDatabase, especialidades: Especialidades){
        especialidades.id = TabelaBDEspecialidades(db).insert(especialidades.ToContentValues())
        assertNotEquals(1, especialidades.id)

    }

    @Before
    fun apagaBaseDados(){
        appContext().deleteDatabase(BDDoentesOpenHelper.NOME)
    }

    @Test
    fun consegueAbrirBaseDados(){
        val openHelper = BDDoentesOpenHelper(appContext())
        val db =openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

    @Test
    fun consegueInserirMedicos(){
        val db = getWritableDatabase()

        //val consultas = Consultas("consulta de dor de peito", "1 de janeiro de 2022")
        //insereConsultas(db, consultas)

        val medico = Medicos("Josefino", "123456789", -1)
        insereMedicos(db, medico)

        val especialidade = Especialidades("Cardiologia")
        insereEspecialidades(db, especialidade)

        db.close()
    }

    @Test
    fun consegueInserirDoentes(){
        val db = getWritableDatabase()


        //val consultas = Consultas("Verificação ao doente Afonso", 1, 1)
        //insereConsultas(db, consultas)


        val doente = Doentes("Afonso", "987654321", "3 de novembro de 2021", -1,-1)
        insereDoentes(db, doente)

        val doencas = Doencas("Dor de peito", -1)
        insereDoencas(db, doencas)
        db.close()


    }
/*
    @Test
    fun consgueInserirConsultas(){
        val db = getWritableDatabase()

        insereConsultas(db, Consultas("Verificação ao doente Afonso", doente_id = 1, medico_id = 1))
        db.close()
    }

*/
}