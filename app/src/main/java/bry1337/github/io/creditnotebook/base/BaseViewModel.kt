package bry1337.github.io.creditnotebook.base

import androidx.lifecycle.ViewModel
import bry1337.github.io.creditnotebook.injection.component.DaggerViewModelInjector
import bry1337.github.io.creditnotebook.injection.component.ViewModelInjector
import bry1337.github.io.creditnotebook.injection.module.NetworkModule
import bry1337.github.io.creditnotebook.presentation.addtransaction.AddTransactionViewModel
import bry1337.github.io.creditnotebook.presentation.home.HomeViewModel
import bry1337.github.io.creditnotebook.presentation.persontransaction.PersonTransactionViewModel

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector.builder().networkModule(NetworkModule).build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is HomeViewModel -> injector.inject(this)

            is AddTransactionViewModel -> injector.inject(this)

            is PersonTransactionViewModel -> injector.inject(this)
        }
    }
}