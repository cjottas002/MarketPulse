package es.market.pulse.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.market.pulse.database.ProductDao
import es.market.pulse.database.UserDao
import es.market.pulse.repository.ProductRepository
import es.market.pulse.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(productDao: ProductDao): ProductRepository {
        return ProductRepository(productDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

}