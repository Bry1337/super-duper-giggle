package bry1337.github.io.creditnotebook.injection.component

import bry1337.github.io.creditnotebook.injection.module.NetworkModule
import bry1337.github.io.creditnotebook.presentation.addtransaction.AddTransactionViewModel
import bry1337.github.io.creditnotebook.presentation.home.HomeViewModel
import bry1337.github.io.creditnotebook.presentation.persontransaction.PersonTransactionViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(homeViewModel: HomeViewModel)

    fun inject(addTransactionViewModel: AddTransactionViewModel)

    fun inject(personTransactionViewModel: PersonTransactionViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(network: NetworkModule): Builder
    }
}