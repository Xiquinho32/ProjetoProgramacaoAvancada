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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.projeto1.databinding.FragmentEliminarDoencasBinding

class EliminarDoencasFragment : Fragment() {
    private var _binding: FragmentEliminarDoencasBinding? = null

    private val binding get() = _binding!!

    private lateinit var doenca: Doencas

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarDoencasBinding.inflate(inflater, container, false)
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

        //medicos = EliminarMedicosFragmentArgs.fromBundle(arguments!!).medicos

        binding.textViewTipoDoenca.text = doenca.tipoDoenca
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId){
            R.id.action_eliminar -> {
                eliminaDoenca()
                true
            }
            R.id.action_cancelar -> {
                voltaListaDoenca()
                true
            }
            else -> false
        }



    private fun eliminaDoenca() {
        val alterDialog = AlertDialog.Builder(requireContext())

        alterDialog.apply {
            setTitle("Eliminar doenca")
            setMessage("Tem a certeza que pertende eliminar doenca?")
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener{ dialogInterface, i ->  })
            setNegativeButton("Eliminar", DialogInterface.OnClickListener{ dialogInterface, i -> confirmaEliminarDoenca() })
            show()
        }
    }

    private fun confirmaEliminarDoenca() {
        val enderecoDoenca = Uri.withAppendedPath(ContentProviderDoentes.ENDERECO_DOENCAS, "${doenca.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoDoenca, null, null)

        if(registosEliminados != 1){
            Snackbar.make(
                binding.textViewTipoDoenca,
                "Erro ao eliminar Tipo de doenca",
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }
        Toast.makeText(requireContext(), "tipo de doenca eliminado com sucesso", Toast.LENGTH_LONG).show()
        voltaListaDoenca()
    }
    private fun voltaListaDoenca() {
        findNavController().navigate(R.id.action_eliminarEspecialidadesFragment_to_listaEspecialidadesFragment)
    }


}
