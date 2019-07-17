package bry1337.github.io.creditnotebook

import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.NavigationUI.*
import bry1337.github.io.creditnotebook.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

  override val isActionBarBackButtonEnabled: Boolean
    get() = true

  override fun setupActivityLayout() {
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
  }

  override fun initView() {
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
