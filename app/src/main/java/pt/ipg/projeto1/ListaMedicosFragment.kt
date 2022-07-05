package pt.ipg.projeto1

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ipg.projeto1.databinding.FragmentListaConsultasBinding

class ListaMedicosFragment: Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    var consultaSeleccionado : Consultas? = null
        get() = field
        set(value) {
            field = value
            (requireActivity() as MainActivity).mostraOpcoesAlterarEliminar(field != null)
        }
}