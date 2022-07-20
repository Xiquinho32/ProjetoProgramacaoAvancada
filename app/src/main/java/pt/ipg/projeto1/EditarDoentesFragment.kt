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
import pt.ipg.projeto1.databinding.FragmentEditarDoentesBinding

class EditarDoentesFragment: Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarDoentesBinding? = null
    private val binding get() = _binding!!

    private var doente: Doentes? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarDoentesBinding.inflate(inflater, container, false)
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
             doente = EditarDoentesFragmentArgs.fromBundle(arguments!!).doentes

            if(doente!= null){
                binding.editTextNome.setText(doente!!.nome)
                binding.editTextDataNascimento.setText(doente!!.dataNascimento)
            }
        }

        LoaderManager.getInstance(this).initLoader(ID_LOADER_DOENCAS, null, this)
    }
    companion object{
        const val ID_LOADER_DOENCAS = 0
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
            ContentProviderDoentes.ENDERECO_DOENCAS,
            TabelaBDDoencas.TODAS_COLUNAS,
            null,
            null,
            "${TabelaBDDoencas.CAMPO_TIPO_DOENCAS}"
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
        val adapterDoencas = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaBDDoencas.CAMPO_TIPO_DOENCAS),
            intArrayOf(android.R.id.text1),
            0
        )
        binding.spinnerDoencas.adapter = adapterDoencas

        atualizaDoencasSelecionada()
    }

    private fun atualizaDoencasSelecionada() {
        if (doente == null) return
        val idDoencas = doente!!.doencas.id

        val ultimaDoencas = binding.spinnerDoencas.count -1

        for (i in 0..ultimaDoencas){
            if(binding.spinnerDoencas.getItemIdAtPosition(i) == idDoencas){
                binding.spinnerDoencas.setSelection(i)
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
        binding.spinnerDoencas.adapter = null
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true

            }
            R.id.action_cancelar ->{
                voltaListaDoentes()
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
        val dataNascimento = binding.editTextDataNascimento.text.toString()
        if(dataNascimento.isBlank()){
            binding.editTextDataNascimento.error = ("preencha a data de nascimento")
            binding.editTextDataNascimento.requestFocus()
            return
        }
        val idDoencas = binding.spinnerDoencas.selectedItemId
        if (idDoencas == Spinner.INVALID_ROW_ID){
            binding.textViewDoencas.error = ("Escolha uma doença")
            binding.spinnerDoencas.requestFocus()
            return
        }

        val doenteGuardado =
            if(doente == null){
                insereDoente(nome,  dataNascimento, idDoencas)
            } else {
                alteraDoente(nome,  dataNascimento, idDoencas)
            }

        if (doenteGuardado){
            Toast.makeText(
                requireContext(),
                "doente guarado com sucesso",
                Toast.LENGTH_LONG
            )
                .show()
            voltaListaDoentes()
        } else {
            Snackbar.make(
                binding.editTextNome,
                "erro ao guardar doente",
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }
    }



    private fun alteraDoente(nome: String, dataNascimento: String, idDoencas: Long): Boolean {
        val doente = Doentes(nome, dataNascimento , Doencas(id = idDoencas))

        val enderecoDoente = Uri.withAppendedPath(ContentProviderDoentes.ENDERECO_DOENTES, "${this.doente!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoDoente, doente.toContentValues(), null, null)

        return registosAlterados == 1
    }
    private fun insereDoente(nome: String, dataNascimento: String, idDoencas: Long): Boolean {
        val doente = Doentes(nome, dataNascimento , Doencas(id = idDoencas))

        val enderecoDoenteInserido = requireActivity().contentResolver.insert(ContentProviderDoentes.ENDERECO_DOENTES, doente.toContentValues())

        return enderecoDoenteInserido != null

    }

    private fun voltaListaDoentes() {
        findNavController().navigate(R.id.action_editarDoentesFragment_to_listaDoentesFragment)
    }


}