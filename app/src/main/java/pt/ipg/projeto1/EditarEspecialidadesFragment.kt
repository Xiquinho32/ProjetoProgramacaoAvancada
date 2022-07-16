package pt.ipg.projeto1

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.projeto1.databinding.FragmentEditarEspecialidadesBinding
import pt.ipg.projeto1.databinding.FragmentEditarMedicosBinding

 class EditarEspecialidadesFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarEspecialidadesBinding? = null
    private val binding get() = _binding!!

    private var especialidade: Especialidades? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarEspecialidadesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_edicao

        if(arguments != null){
            // especialidade = EditarEspecialidadesFragment.fromBundle(arguments!!).medico

            if(especialidade != null){
                binding.editTextTipoEspecialidade.setText(especialidade!!.tipoEspecialidades)
            }
        }

        LoaderManager.getInstance(this).initLoader(ID_LOADER_ESPECIALIDADES, null, this)
    }
    companion object{
        const val ID_LOADER_ESPECIALIDADES = 0
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    /*
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            ContentProviderDoentes.ENDERECO_ESPECIALIDADES,
            TabelaBDEspecialidades.TODAS_COLUNAS,
            null,
            null,
            "${TabelaBDEspecialidades.CAMPO_TIPO_ESPECIALIDADES}"
        )


     */

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */




    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */


    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true

            }
            R.id.action_cancelar ->{
                voltaListaEspecialidades()
                true
            }
            else -> false

        }


    private fun guardar() {
        val tipoEspecialidade = binding.editTextTipoEspecialidade.text.toString()
        if(tipoEspecialidade.isBlank()){
            binding.editTextTipoEspecialidade.error = "preencha o tipo de especialidade"
            binding.editTextTipoEspecialidade.requestFocus()
            return
        }



        val especialidadeGuardado =
            if(especialidade == null){
                insereEspecialidade(tipoEspecialidade)
            } else {
                alteraEspecialidade(tipoEspecialidade)
            }

        if (especialidadeGuardado){
            Toast.makeText(
                requireContext(),
                getString(R.string.medico_guardado_sucesso),
                Toast.LENGTH_LONG
            )
                .show()
            voltaListaEspecialidades()
        } else {
            Snackbar.make(
                binding.editTextTipoEspecialidade,
                "erro ao guardar tipo de especialidade",
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }
    }

    private fun insereEspecialidade(tipoEspecialidade: String): Boolean {
        val especialidade = Especialidades(tipoEspecialidade)

        val enderecoEspecialidadeInserido = requireActivity().contentResolver.insert(ContentProviderDoentes.ENDERECO_ESPECIALIDADES, especialidade.toContentValues())

        return enderecoEspecialidadeInserido != null
    }


    private fun alteraEspecialidade(tipoEspecialidade: String): Boolean {

        val especialidade = Especialidades(tipoEspecialidade)

        val enderecoEspecialidade = Uri.withAppendedPath(ContentProviderDoentes.ENDERECO_ESPECIALIDADES, "${this.especialidade!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoEspecialidade,especialidade.toContentValues(), null, null)

        return registosAlterados == 1
    }


    private fun voltaListaEspecialidades(){
        findNavController().navigate(R.id.action_editarEspecialidadesFragment_to_listaEspecialidadesFragment)
    }

     /**
      * Instantiate and return a new Loader for the given ID.
      *
      *
      * This will always be called from the process's main thread.
      *
      * @param id The ID whose loader is to be created.
      * @param args Any arguments supplied by the caller.
      * @return Return a new Loader instance that is ready to start loading.
      */
     override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
         TODO("Not yet implemented")
     }

     /**
      * Called when a previously created loader has finished its load.  Note
      * that normally an application is *not* allowed to commit fragment
      * transactions while in this call, since it can happen after an
      * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
      *
      *
      * This function is guaranteed to be called prior to the release of
      * the last data that was supplied for this Loader.  At this point
      * you should remove all use of the old data (since it will be released
      * soon), but should not do your own release of the data since its Loader
      * owns it and will take care of that.  The Loader will take care of
      * management of its data so you don't have to.  In particular:
      *
      *
      *  *
      *
      *The Loader will monitor for changes to the data, and report
      * them to you through new calls here.  You should not monitor the
      * data yourself.  For example, if the data is a [android.database.Cursor]
      * and you place it in a [android.widget.CursorAdapter], use
      * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
      * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
      * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
      * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
      * from doing its own observing of the Cursor, which is not needed since
      * when a change happens you will get a new Cursor throw another call
      * here.
      *  *  The Loader will release the data once it knows the application
      * is no longer using it.  For example, if the data is
      * a [android.database.Cursor] from a [android.content.CursorLoader],
      * you should not call close() on it yourself.  If the Cursor is being placed in a
      * [android.widget.CursorAdapter], you should use the
      * [android.widget.CursorAdapter.swapCursor]
      * method so that the old Cursor is not closed.
      *
      *
      *
      * This will always be called from the process's main thread.
      *
      * @param loader The Loader that has finished.
      * @param data The data generated by the Loader.
      */
     override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
         TODO("Not yet implemented")
     }

     /**
      * Called when a previously created loader is being reset, and thus
      * making its data unavailable.  The application should at this point
      * remove any references it has to the Loader's data.
      *
      *
      * This will always be called from the process's main thread.
      *
      * @param loader The Loader that is being reset.
      */
     override fun onLoaderReset(loader: Loader<Cursor>) {
         TODO("Not yet implemented")
     }

     /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */


    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */

}
