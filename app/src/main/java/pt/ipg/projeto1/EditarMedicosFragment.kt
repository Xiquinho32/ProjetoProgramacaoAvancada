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
import androidx.core.app.Person.fromBundle
import androidx.core.graphics.isSrgb
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.projeto1.databinding.FragmentEditarMedicosBinding

class EditarMedicosFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarMedicosBinding? = null

    private val binding get() = _binding!!

    private var medico: Medicos? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarMedicosBinding.inflate(inflater, container, false)
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
            medico = EditarMedicosFragmentArgs.fromBundle(arguments!!).medicos

            if(medico != null){
                binding.editTextNome.setText(medico!!.nome)
                binding.editTextCartaoCidadao.setText(medico!!.cc)
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
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            ContentProviderDoentes.ENDERECO_ESPECIALIDADES,
            TabelaBDEspecialidades.TODAS_COLUNAS,
            null,
            null,
            "${TabelaBDEspecialidades.CAMPO_TIPO_ESPECIALIDADES}"
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
        val adapterEspecialidades = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaBDEspecialidades.CAMPO_TIPO_ESPECIALIDADES),
            intArrayOf(android.R.id.text1),
            0
        )
        binding.spinnerEspecialidades.adapter = adapterEspecialidades

        atualizaEspecialidadesSelecionada()
    }

    private fun atualizaEspecialidadesSelecionada() {
        if (medico == null) return
        val idEspecialidades = medico!!.especialidades.id

        val ultimaEspecialidades = binding.spinnerEspecialidades.count -1

        for (i in 0..ultimaEspecialidades){
            if(binding.spinnerEspecialidades.getItemIdAtPosition(i) == idEspecialidades){
                binding.spinnerEspecialidades.setSelection(i)
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
        if ( _binding == null) return
        binding.spinnerEspecialidades.adapter = null
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true

            }
            R.id.action_cancelar ->{
                voltaListaMedicos()
                true
            }
            else -> false

        }


    private fun guardar() {
        val nome = binding.editTextNome.text.toString()
        if(nome.isBlank()){
            binding.editTextNome.error = getString(R.string.nome_obrigatorio)
            binding.editTextNome.requestFocus()
            return
        }
        val cc  = binding.editTextCartaoCidadao.text.toString()
        if(cc.isBlank()){
            binding.editTextCartaoCidadao.error = getString(R.string.cartaoCidadao_obrigatorio)
            binding.editTextCartaoCidadao.requestFocus()
            return
        }
        val idEspecialidades = binding.spinnerEspecialidades.selectedItemId
        if (idEspecialidades == Spinner.INVALID_ROW_ID){
            binding.textViewEspecialidades.error = getString(R.string.especialidade_obrigatoria)
            binding.spinnerEspecialidades.requestFocus()
            return
        }

        val medicoGuardado =
            if(medico == null){
                insereMedico(nome, cc, idEspecialidades)
            } else {
                alteraMedico(nome, cc, idEspecialidades)
            }

        if (medicoGuardado){
            Toast.makeText(requireContext(), getString(R.string.medico_guardado_sucesso), Toast.LENGTH_LONG)
                .show()
            voltaListaMedicos()
        } else {
            Snackbar.make(binding.editTextNome, getString(R.string.erro_guardar_medico), Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }
    private fun alteraMedico(nome: String, cc: String, idEspecialidades: Long): Boolean {
        val medico = Medicos(nome, cc, Especialidades(id = idEspecialidades))

        val enderecoMedico = Uri.withAppendedPath(ContentProviderDoentes.ENDERECO_MEDICOS, "${this.medico!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoMedico, medico.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereMedico(nome: String, cc: String, idEspecialidades: Long): Boolean {
        val medico = Medicos(nome, cc, Especialidades(id = idEspecialidades))

        val enderecoMedicoInserido = requireActivity().contentResolver.insert(ContentProviderDoentes.ENDERECO_MEDICOS, medico.toContentValues())

        return enderecoMedicoInserido != null

    }



    private fun voltaListaMedicos() {
        findNavController().navigate(R.id.action_editarMedicosFragment_to_listaMedicosFragment)
    }


}