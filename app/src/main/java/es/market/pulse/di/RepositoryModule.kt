package es.market.pulse.di

import es.market.pulse.database.ProductDao
import es.market.pulse.database.UserDao
import es.market.pulse.repository.ProductRepository
import es.market.pulse.repository.UserRepository

object RepositoryModule {

    fun provideProductRepository(productDao: ProductDao): ProductRepository {
        return ProductRepository(productDao)
    }

    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

}