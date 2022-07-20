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
import pt.ipg.projeto1.databinding.FragmentEliminarEspecialidadesBinding

class EliminarEspecialidadesFragment  : Fragment() {
    private var _binding: FragmentEliminarEspecialidadesBinding? = null

    private val binding get() = _binding!!

    private lateinit var especialidade: Especialidades

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarEspecialidadesBinding.inflate(inflater, container, false)
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

       especialidade= EliminarEspecialidadesFragmentArgs.fromBundle(arguments!!).especialidade

        binding.textViewTipoEspecialidades.text = especialidade.tipoEspecialidades
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId){
            R.id.action_eliminar -> {
                eliminaEspecialidade()
                true
            }
            R.id.action_cancelar -> {
                voltaListaEspecialidades()
                true
            }
            else -> false
        }



    private fun eliminaEspecialidade() {
        val alterDialog = AlertDialog.Builder(requireContext())

        alterDialog.apply {
            setTitle("Eliminar especialidade")
            setMessage("Tem a certeza que pertende eliminar especialidade?")
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener{ dialogInterface, i ->  })
            setNegativeButton("Eliminar", DialogInterface.OnClickListener{ dialogInterface, i -> confirmaEliminarEspecialidade() })
            show()
        }
    }

    private fun confirmaEliminarEspecialidade() {
        val enderecoEspecialidade = Uri.withAppendedPath(ContentProviderDoentes.ENDERECO_ESPECIALIDADES, "${especialidade.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoEspecialidade, null, null)

        if(registosEliminados != 1){
            Snackbar.make(
                binding.textViewTipoEspecialidades,
                "Erro ao eliminar Tipo de especialidade",
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }
        Toast.makeText(requireContext(), "tipo de especialidade eliminado com sucesso", Toast.LENGTH_LONG).show()
        voltaListaEspecialidades()
    }
    private fun voltaListaEspecialidades() {
        findNavController().navigate(R.id.action_eliminarEspecialidadesFragment_to_listaEspecialidadesFragment)
    }


}
