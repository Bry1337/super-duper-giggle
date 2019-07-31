package bry1337.github.io.creditnotebook.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.*
import bry1337.github.io.creditnotebook.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme_NoActionBar)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    initView()
  }

  private fun initView() {
    setupNavigation()
  }

  override fun onSupportNavigateUp(): Boolean {
    return navigateUp(findNavController(R.id.nav_host_fragment), null)
  }

  private fun setupNavigation() {
    val navController = findNavController(R.id.nav_host_fragment)
    // Update action bar to reflect navigation
    setupActionBarWithNavController(this, navController, null)
    // Tie nav graph to items in nav drawer
    setupWithNavController(toolbar, navController)
  }
}
