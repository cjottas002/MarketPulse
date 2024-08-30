package es.market.pulse.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import es.market.pulse.model.Address

@Dao
interface AddressDao {

    @Insert
    suspend fun insertAddress(address: Address): Long

    @Update
    suspend fun updateAddress(address: Address)

    @Delete
    suspend fun deleteAddress(address: Address)

    @Query("SELECT * FROM address WHERE id = :id")
    suspend fun getAddressById(id: Int): Address?

    @Query("SELECT * FROM address")
    suspend fun getAllAddresses(): List<Address>
}

