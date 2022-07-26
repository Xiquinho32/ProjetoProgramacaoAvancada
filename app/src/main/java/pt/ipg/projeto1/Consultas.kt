package pt.ipg.projeto1

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Consultas(
    var data: String,
    var medicos: Medicos,
    var doentes: Doentes,
    var id: Long = -1
) : Serializable{
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDConsultas.CAMPO_DATA, data)
        valores.put(TabelaBDConsultas.CAMPO_DOENTE_ID, doentes.id)
        valores.put(TabelaBDConsultas.CAMPO_MEDICO_ID, medicos.id)
        //valores.put(TabelaBDConsultas.CAMPO_ID, id)

        return valores;
    }
    companion object{
        fun fromCursor(cursor: Cursor):Consultas {
            //Tabela consultas
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posData = cursor.getColumnIndex(TabelaBDConsultas.CAMPO_DATA)
            val posIdMedicos = cursor.getColumnIndex(TabelaBDConsultas.CAMPO_MEDICO_ID)
            val posIdDoentes = cursor.getColumnIndex(TabelaBDConsultas.CAMPO_DOENTE_ID)


            val posNomeMedicos = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_NOME_MEDICO)
            val posCCMedicos = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_CC)
            //val posTipoEspecialidades = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_ESPECIALIDADES_ID)
            val posTipoEspecialidades = cursor.getColumnIndex(TabelaBDEspecialidades.CAMPO_TIPO_ESPECIALIDADES)


            val posNomeDoentes = cursor.getColumnIndex(TabelaBDDoentes.CAMPO_NOME_DOENTE)
            val posDataNascimentoDoentes = cursor.getColumnIndex(TabelaBDDoentes.CAMPO_DATA_NASCIMENTO)
            val posTipoDoencas = cursor.getColumnIndex(TabelaBDDoencas.CAMPO_TIPO_DOENCAS)
            //val posTipoDoencas2 = cursor.getColumnIndex(TabelaBDDoencas.CAMPO_TIPO_DOENCAS)



            //consulta
            val id = cursor.getLong(posId)
            val data = cursor.getString(posData)


            //medicos
            val idMedico = cursor.getLong(posIdMedicos)
            val nomeMedico = cursor.getString(posNomeMedicos)
            val ccMedicos = cursor.getString(posCCMedicos)
            val especialidadesMedicos = cursor.getString(posTipoEspecialidades)
            //val especialidadesMedicos2 = cursor.getString(posTipoEspecialidades2)

            val especialidades = Especialidades(especialidadesMedicos)

            val medico = Medicos(nomeMedico, ccMedicos, especialidades ,idMedico )
            //val medico = Medicos(nomeMedico, ccMedicos, especialidades = especialidadesMedicos,idMedico)
            //val medico = Medicos(nomeMedico, ccMedicos, especialidades = Especialidades(especialidadesMedicos), idMedico)


            //doentes
            val idDoentes = cursor.getLong(posIdDoentes)
            val nomeDoentes = cursor.getString(posNomeDoentes)
            val dataNascimentoDoentes = cursor.getString(posDataNascimentoDoentes)
            val doencasDoentes = cursor.getString(posTipoDoencas)
            //val doencasDoentes2= cursor.getString(posTipoDoencas2)

            val doenca = Doencas(doencasDoentes)

            val doente = Doentes(nomeDoentes, dataNascimentoDoentes, doenca, idDoentes)

            //val doente = Doentes(nomeDoentes, dataNascimentoDoentes, doencas = Doencas(doencasDoentes2), idDoentes)
            return Consultas(data, medico, doente, id)




/*
            //Tabela medicos
            val posNomeMedicos = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_NOME_MEDICO)
            val posccMedicos = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_CC)
            val posEspecialidades = cursor.getColumnIndex(TabelaBDMedicos.CAMPO_ESPECIALIDADES_ID)

            //Tabela doentes
            val posNomeDoentes = cursor.getColumnIndex(TabelaBDDoentes.CAMPO_NOME_DOENTE)
            val posDataNascimentoDoentes =
                cursor.getColumnIndex(TabelaBDDoentes.CAMPO_DATA_NASCIMENTO)

            //Tabela Especialidades
            // val posTipoEspecialidades = cursor.getColumnIndex(TabelaBDEspecialidades.CAMPO_TIPO_ESPECIALIDADES)


*/

            //return valores
           // return Consultas(data, medico,doente , id)
        }


}}
