package pt.ipg.projeto1

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.projeto1.databinding.FragmentEliminarMedicosBinding

class EliminarMedicosFragment : Fragment(){
    private var _binding: FragmentEliminarMedicosBinding? = null

    private val binding get() = _binding!!

    private lateinit var medicos: Medicos

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarMedicosBinding.inflate(inflater, container, false)
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

        medicos = EliminarMedicosFragmentArgs.fromBundle(arguments!!).medico

        binding.textViewNome.text = medicos.nome
        binding.textViewCartaoCidadao.text = medicos.cc
        binding.textViewEspecialidade.text = medicos.especialidades.tipoEspecialidades
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId){
            R.id.action_eliminar -> {
                eliminaMedico()
                true
            }
            R.id.action_cancelar -> {
                voltaListaMedicos()
                true
            }
            else -> false
        }



    private fun eliminaMedico() {
        val alterDialog = AlertDialog.Builder(requireContext())

        alterDialog.apply {
            setTitle("Eliminar medico")
            setMessage("Tem a certeza que pertende eliminar o medico?")
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener{ dialogInterface, i ->  })
            setNegativeButton("Eliminar", DialogInterface.OnClickListener{ dialogInterface, i -> confirmaEliminarMedico() })
            show()
        }
    }

    private fun confirmaEliminarMedico() {
        val enderecoMedico = Uri.withAppendedPath(ContentProviderDoentes.ENDERECO_MEDICOS, "${medicos.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoMedico, null, null)

        if(registosEliminados != 1){
            Snackbar.make(
                binding.textViewNome,
                "Erro ao eliminar medico",
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }
        Toast.makeText(requireContext(), "medico eliminado com sucesso", Toast.LENGTH_LONG).show()
        voltaListaMedicos()
    }
    private fun voltaListaMedicos() {
        findNavController().navigate(R.id.action_eliminarMedicosFragment_to_listaMedicosFragment)
    }


}