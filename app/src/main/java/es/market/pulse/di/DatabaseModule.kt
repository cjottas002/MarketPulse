package es.market.pulse.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.market.pulse.database.MarketPulseDatabase
import es.market.pulse.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MarketPulseDatabase {
        return Room.databaseBuilder(
            context,
            MarketPulseDatabase::class.java,
            Constants.DATABASE_NAME
        ).addCallback(MarketPulseDatabase.MarketPulseDatabaseCallback(context)).build()
    }

    @Provides
    fun provideUserDao(database: MarketPulseDatabase) = database.userDao()

    @Provides
    fun provideProductDao(database: MarketPulseDatabase) = database.productDao()

    @Provides
    fun provideRoleDao(database: MarketPulseDatabase) = database.roleDao()

    @Provides
    fun provideCartDao(database: MarketPulseDatabase) = database.cartDao()

    @Provides
    fun provideCartItemDao(database: MarketPulseDatabase) = database.cartItemDao()

    @Provides
    fun provideOrderDao(database: MarketPulseDatabase) = database.orderDao()

    @Provides
    fun provideAddressDao(database: MarketPulseDatabase) = database.addressDao()
}
