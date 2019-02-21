package lehrbaum.de.onenightcomps

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import lehrbaum.de.onenightcomps.dataaccess.repositoriesModule
import lehrbaum.de.onenightcomps.databinding.NavDrawerHeaderBinding
import lehrbaum.de.onenightcomps.viewmodel.AppViewModel
import lehrbaum.de.onenightcomps.viewmodel.NavDrawerViewModel
import org.koin.android.ext.android.startKoin

class MainActivity : AppCompatActivity() {

	private lateinit var drawerLayout: DrawerLayout
	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var navController: NavController
	private lateinit var navView: NavigationView

	private lateinit var navDrawerViewModel: NavDrawerViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setUpKoin()

		navDrawerViewModel = ViewModelProviders.of(this).get(NavDrawerViewModel::class.java)
		setUpNavigation()
		setUpSideDrawerAndAppBar()
	}

	private fun setUpKoin() {
		startKoin(this, listOf(repositoriesModule))
	}

	private fun setUpNavigation() {
		navController = findNavController(R.id.mainNavHostFragment)
		navController.addOnDestinationChangedListener { _, destination, _ ->
			Log.d("MainActivity", "destination: $destination")
		}
		navView = findViewById(R.id.nav_view)
		navView.setupWithNavController(navController)
		navDrawerViewModel.initWithItemVisibilityToggler(this::setMenuItemVisibility)
		setUpLogoutAction()

		// use the AppViewModel for navigation
		AppViewModel.nextActionLiveEvent.observe(this, Observer { action ->
			navController.navigate(action)
		})
	}

	private fun setUpLogoutAction() {
		navView.menu.findItem(R.id.logoutAction).setOnMenuItemClickListener {
			navDrawerViewModel.onLogoutClicked()
			true
		}
	}

	private fun setMenuItemVisibility(menuItemId: Int, visibility: Boolean) {
		navView.menu.findItem(menuItemId)?.isVisible = visibility
	}

	private fun setUpSideDrawerAndAppBar() {
		drawerLayout = findViewById(R.id.drawer_layout)
		setSupportActionBar(findViewById(R.id.toolbar))
		appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
		setupActionBarWithNavController(navController, appBarConfiguration)
		setUpDrawerHeader()
	}

	private fun setUpDrawerHeader() {
		val navView = findViewById<NavigationView>(R.id.nav_view)
		val inflater = LayoutInflater.from(this)
		val binding = NavDrawerHeaderBinding.inflate(inflater)
		binding.lifecycleOwner = this
		binding.viewModel = navDrawerViewModel
		navView.addHeaderView(binding.root)
	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}

	override fun onBackPressed() {
		if (drawerLayout.isDrawerOpen(GravityCompat.START))
			drawerLayout.closeDrawer(GravityCompat.START)
		else
			super.onBackPressed()
	}
}
