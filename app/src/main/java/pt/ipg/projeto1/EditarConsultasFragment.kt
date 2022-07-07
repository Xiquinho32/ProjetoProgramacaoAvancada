package pt.ipg.projeto1

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.Person.fromBundle
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.projeto1.databinding.FragmentEditarConsultasBinding

class EditarConsultasFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarConsultasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var consulta: Consultas? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarConsultasBinding.inflate(inflater, container, false)
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

        if (arguments != null) {
           // consulta = EditarConsultasFragment.fromBundle(requireArguments()).consulta
            if (consulta != null) {
                binding.editTextData.setText(consulta!!.data)
            }
        }
        LoaderManager.getInstance(this).initLoader(ID_LOADER_MEDICOS, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_DOENTES, null, this)
    }

    companion object {
        const val ID_LOADER_MEDICOS = 0
        const val ID_LOADER_DOENTES = 0

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
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            ContentProviderDoentes.ENDERECO_CONSULTAS,
            TabelaBDConsultas.TODAS_COLUNAS,
            null,
            null,
            "${TabelaBDConsultas.CAMPO_DATA}"
        )

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
        val adapterDoentes = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaBDDoentes.CAMPO_NOME_DOENTE),
            intArrayOf(android.R.id.text1),
            0
        )
        val adapterMedicos = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaBDMedicos.CAMPO_NOME_MEDICO),
            intArrayOf(android.R.id.text1),
            0
        )


        binding.spinnerDoentes.adapter = adapterDoentes
        binding.spinnerMedicos.adapter = adapterMedicos

        atualizaDoenteSelecionada()
        atualizaMedicosSelecionada()
    }
    private fun atualizaDoenteSelecionada() {
        if (consulta == null) return
        val idDoentes = consulta!!.doentes.id

        val ultimoDoentes = binding.spinnerDoentes.count - 1

        for (i in 0..ultimoDoentes) {
            if (binding.spinnerDoentes.getItemIdAtPosition(i) == idDoentes) {
                binding.spinnerDoentes.setSelection(i)
                return
            }
        }
    }
    private fun atualizaMedicosSelecionada() {
        if (consulta == null) return
        val idMedicos = consulta!!.medicos.id

        val ultimoMedicos = binding.spinnerMedicos.count - 1

        for (i in 0..ultimoMedicos) {
            if (binding.spinnerMedicos.getItemIdAtPosition(i) == idMedicos) {
                binding.spinnerMedicos.setSelection(i)
                return
            }
        }
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
        if (_binding == null) return
        binding.spinnerDoentes.adapter = null
        binding.spinnerMedicos.adapter = null
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaConsultas()
                true
            }
            else -> false
        }

    private fun guardar() {
        val data = binding.editTextData.text.toString()
        if (data.isBlank()) {
            binding.editTextData.error = getString(R.string.Data_obrigatorio)
            binding.editTextData.requestFocus()
            return
        }

        val idDoentes = binding.spinnerDoentes.selectedItemId
        if (idDoentes == Spinner.INVALID_ROW_ID) {
            binding.textViewDoente.error = getString(R.string.Doente_obrigatorio)
            binding.spinnerDoentes.requestFocus()
            return
        }
        val idMedicos = binding.spinnerMedicos.selectedItemId
        if (idMedicos == Spinner.INVALID_ROW_ID) {
            binding.textViewMedico.error = getString(R.string.Medico_obrigatorio)
            binding.spinnerMedicos.requestFocus()
            return
        }

        val consultaGuardado =
            if (consulta == null) {
                insereConsulta(data, idDoentes, idMedicos)
            } else {
                alteraConsulta(data, idDoentes, idMedicos)
            }
        if (consultaGuardado) {
            Toast.makeText(
                requireContext(),
                getString(R.string.consulta_guardado_sucesso),
                Toast.LENGTH_LONG
            )
                .show()
            voltaListaConsultas()
        } else {
            Snackbar.make(
                binding.editTextData,
                getString(R.string.erro_guardar_consulta),
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }
    }

    private fun alteraConsulta(data: String, idDoentes : Long, idMedicos: Long) : Boolean {
        val consulta = Consultas(data, Medicos(id = idMedicos, cc = "", idEspecialidades = 2), Doentes(id = idDoentes, cc = "", dataNascimento = "", idDoencas = 2))

        val enderecoConsultas = Uri.withAppendedPath(ContentProviderDoentes.ENDERECO_CONSULTAS, "${this.consulta!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoConsultas, consulta.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereConsulta(data: String, idDoentes : Long, idMedicos: Long): Boolean {
        val consulta = Consultas(data, Medicos(id = idMedicos, cc = "", idEspecialidades = 2), Doentes(id = idDoentes, cc = "", dataNascimento = "", idDoencas = 2))

        val enderecoConsultasInserido = requireActivity().contentResolver.insert(ContentProviderDoentes.ENDERECO_CONSULTAS, consulta.toContentValues())

        return enderecoConsultasInserido != null
    }

    private fun voltaListaConsultas() {
        findNavController().navigate(R.id.action_menuPrincipalFragment_to_listaConsultasFragment)
    }

}