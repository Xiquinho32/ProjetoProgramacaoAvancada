package pt.ipg.projeto1

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.projeto1.databinding.FragmentEliminarConsultasBinding

class EliminarConsultasFragment : Fragment(){
    private var _binding: FragmentEliminarConsultasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var consulta: Consultas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarConsultasBinding.inflate(inflater, container, false)
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

        consulta = EliminarConsultasFragment.fromBundle(requireArguments()).consulta

        binding.textViewData.text = consulta?.data?: ""
        binding.textViewMedico.text = consulta?.medicos?.nome?: ""
        binding.textViewDoente.text = consulta?.doentes?.nome?: ""
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaConsulta()
                true
            }
            R.id.action_cancelar -> {
                voltaListaConsulta()
                true
            }
            else -> false
        }

    private fun eliminaConsulta() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_consulta_label)
            setMessage(getString(R.string.confirma_eliminar_consulta))
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(getString(R.string.eliminar), DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarLivro() })
            show()
        }
    }

    private fun confirmaEliminarConsulta() {
        val enderecoConsultas = Uri.withAppendedPath(ContentProviderDoentes.ENDERECO_CONSULTAS, "${consulta.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoConsultas, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewData,
                getString(R.string.erro_eliminar_consulta),
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), getString(R.string.consulta_eliminado_sucesso), Toast.LENGTH_LONG).show()
        voltaListaConsulta()
    }

    private fun voltaListaConsulta() {
        val acao = EliminarConsultasFragment.action_eliminarConsultasFragment_to_listaConsultasFragment()
        findNavController().navigate(acao)
    }


}