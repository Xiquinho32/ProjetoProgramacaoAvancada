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


}