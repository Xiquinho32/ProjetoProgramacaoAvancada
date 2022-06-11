package pt.ipg.projeto1

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert

class BaseDadosTest {
    private fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase() :SQLiteDatabase{
        val openHelper = BDDoentesOpenHelper(appContext())
        return openHelper.writableDatabase
    }

    private fun insereDoentes(db: SQLiteDatabase, doentes: Doentes){
        doentes.id = TabelaBDDoentes(db).insert(doentes.toContentValues())
        Assert.assertNotEquals(-1, doentes.id)
    }


}