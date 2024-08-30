package es.market.pulse.ui.viewmodels

import android.content.Context
import es.market.pulse.database.MarketPulseDatabase
import es.market.pulse.repository.ProductRepository
import es.market.pulse.repository.UserRepository

object ViewModelFactory {

    fun provideLoginViewModel(context: Context): LoginViewModel {
        return createViewModel(context, ViewModelFactory::provideUserRepository, ::LoginViewModel)
    }

    private fun <T, R> createViewModel(
        context: Context,
        repositoryProvider: (Context) -> R,
        viewModelConstructor: (R) -> T
    ): T {
        val repository = repositoryProvider(context)
        return viewModelConstructor(repository)
    }

    private fun provideProductRepository(context: Context): ProductRepository {
        val productDao = MarketPulseDatabase.getDatabase(context).productDao()
        return ProductRepository(productDao)
    }

    private fun provideUserRepository(context: Context): UserRepository {
        val userDao = MarketPulseDatabase.getDatabase(context).userDao()
        return UserRepository(userDao)
    }
}