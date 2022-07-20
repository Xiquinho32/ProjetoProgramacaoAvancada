package pt.ipg.projeto1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import pt.ipg.projeto1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var idMenuAtual = R.menu.menu_main
        get() = field
        set(value) {
            if (value != field) {
                field = value
                invalidateOptionsMenu()
            }
        }

    var menu: Menu? = null

    var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(idMenuAtual, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        val opcaoProcessada: Boolean

        if (fragment is MenuPrincipalFragment) {
            opcaoProcessada = (fragment as MenuPrincipalFragment).processaOpcaoMenu(item)
        } else if (fragment is ListaConsultasFragment) {
            opcaoProcessada = (fragment as ListaConsultasFragment).processaOpcaoMenu(item)
        } else if (fragment is EditarConsultasFragment) {
            opcaoProcessada = (fragment as EditarConsultasFragment).processaOpcaoMenu(item)
        } else if (fragment is EliminarConsultasFragment) {
            opcaoProcessada = (fragment as EliminarConsultasFragment).processaOpcaoMenu(item)
        } else if (fragment is ListaMedicosFragment){
            opcaoProcessada = (fragment as ListaMedicosFragment).processaOpcaoMenu(item)
        } else if (fragment is EditarMedicosFragment){
            opcaoProcessada = (fragment as EditarMedicosFragment).processaOpcaoMenu(item)
        } else if (fragment is EliminarMedicosFragment){
            opcaoProcessada = (fragment as EliminarMedicosFragment).processaOpcaoMenu(item)
        } else if (fragment is ListaEspecialidadesFragment){
            opcaoProcessada = (fragment as ListaEspecialidadesFragment).processaOpcaoMenu(item)
        } else if (fragment is EditarEspecialidadesFragment){
            opcaoProcessada = (fragment as EditarEspecialidadesFragment).processaOpcaoMenu(item)
        } else if (fragment is EliminarEspecialidadesFragment){
            opcaoProcessada = (fragment as EliminarEspecialidadesFragment).processaOpcaoMenu(item)
        }else if (fragment is ListaDoencasFragment) {
            opcaoProcessada = (fragment as ListaDoencasFragment).processaOpcaoMenu(item)
        } else if (fragment is EditarDoencasFragment) {
            opcaoProcessada = (fragment as EditarDoencasFragment).processaOpcaoMenu(item)
        } else if (fragment is EliminarDoencasFragment) {
            opcaoProcessada = (fragment as EliminarDoencasFragment).processaOpcaoMenu(item)
        } else if (fragment is ListaDoentesFragment) {
            opcaoProcessada = (fragment as ListaDoentesFragment).processaOpcaoMenu(item)
        } else if (fragment is EditarDoentesFragment) {
            opcaoProcessada = (fragment as EditarDoentesFragment).processaOpcaoMenu(item)
        } else if (fragment is EliminarDoentesFragment) {
            opcaoProcessada = (fragment as EliminarDoentesFragment).processaOpcaoMenu(item)
        }
        else {
            opcaoProcessada = false
        }

        if (opcaoProcessada) return true

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun mostraOpcoesAlterarEliminar(mostra: Boolean) {
        menu!!.findItem(R.id.action_alterar).setVisible(mostra)
        menu!!.findItem(R.id.action_eliminar).setVisible(mostra)
    }

    fun atualizaData(id_string_data: String) {
        binding.toolbar.setTitle(id_string_data)
    }
}

