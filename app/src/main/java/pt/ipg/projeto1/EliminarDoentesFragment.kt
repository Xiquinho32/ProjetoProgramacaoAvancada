package pt.ipg.projeto1

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.Person.fromBundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.projeto1.databinding.FragmentEliminarDoentesBinding

class EliminarDoentesFragment: Fragment(){
    private var _binding: FragmentEliminarDoentesBinding? = null

    private val binding get() = _binding!!

    private lateinit var doentes: Doentes

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarDoentesBinding.inflate(inflater, container, false)
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
        activity.idMenuAtual = R.menu.menu_eliminar

        //doentes = EliminarDoentesFragment.fromBundle(arguments!!).medicos

        binding.textViewNome.text = doentes.nome
        binding.textViewCC.text = doentes.cc
        binding.textViewDataNascimento.text = doentes.dataNascimento
        binding.textViewTipoDoenca.text = doentes.doencas.tipoDoenca
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId){
            R.id.action_eliminar -> {
                eliminaDoente()
                true
            }
            R.id.action_cancelar -> {
                voltaListaDoente()
                true
            }
            else -> false
        }



    private fun eliminaDoente() {
        val alterDialog = AlertDialog.Builder(requireContext())

        alterDialog.apply {
            setTitle("Eliminar doente")
            setMessage("Tem a certeza que pertende eliminar o doente?")
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener{ dialogInterface, i ->  })
            setNegativeButton("Eliminar", DialogInterface.OnClickListener{ dialogInterface, i -> confirmaEliminarDoente() })
            show()
        }
    }

    private fun confirmaEliminarDoente() {
        val enderecoDoente = Uri.withAppendedPath(ContentProviderDoentes.ENDERECO_DOENTES, "${doentes.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoDoente, null, null)

        if(registosEliminados != 1){
            Snackbar.make(
                binding.textViewNome,
                "Erro ao eliminar doente",
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }
        Toast.makeText(requireContext(), "doente eliminado com sucesso", Toast.LENGTH_LONG).show()
        voltaListaDoente()
    }
    private fun voltaListaDoente() {
        findNavController().navigate(R.id.action_eliminarDoentesFragment_to_listaDoentesFragment)
    }


}