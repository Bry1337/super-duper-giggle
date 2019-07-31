package bry1337.github.io.creditnotebook.base

import android.content.Intent
import bry1337.github.io.creditnotebook.R

/**
 * Created by edwardbryan.abergas on 07/16/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class SplashActivity : BaseActivity() {
    override val isActionBarBackButtonEnabled: Boolean
        get() = false

    override fun setupActivityLayout() {
        setContentView(R.layout.activity_splash_screen)
    }

    override fun initView() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}