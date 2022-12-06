package ru.zfix27r.todo.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.zfix27r.todo.R
import ru.zfix27r.todo.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.main_toolbar)

        setNavController()
        setActionBar()
        setSharedPreferenceListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        if (p1 == getString(R.string.theme_key)) setTheme()
    }

    override fun onDestroy() {
        super.onDestroy()
        getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }

    fun showBottomAppBar() {
        findViewById<BottomAppBar>(R.id.bottom).visibility = View.VISIBLE
    }

    fun hideBottomAppBar() {
        findViewById<BottomAppBar>(R.id.bottom).visibility = View.GONE
    }

    fun showBottomAppBarWithFab() {
        showBottomAppBar()
        findViewById<FloatingActionButton>(R.id.fab).show()
    }

    fun hideBottomAppBarWithFab() {
        hideBottomAppBar()
        findViewById<FloatingActionButton>(R.id.fab).hide()
    }

    fun setTitleBottomAppBar(title: String = "") {
        findViewById<TextView>(R.id.bottom_title).text = title
    }

    private fun setTheme() {
        val currentTheme = getDefaultSharedPreferences(this).getString(
            getString(R.string.theme_key),
            getString(R.string.theme_default_name)
        )
        val themeLight = getString(R.string.theme_light_name)
        val themeDark = getString(R.string.theme_dark_name)

        when (currentTheme) {
            themeLight -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            themeDark -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun setNavController() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHost.navController
    }

    private fun setActionBar() {
        setSupportActionBar(toolbar)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawer)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setupWithNavController(navController)
    }

    private fun setSharedPreferenceListener() {
        getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)
    }
}