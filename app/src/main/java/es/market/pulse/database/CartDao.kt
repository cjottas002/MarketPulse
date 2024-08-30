package es.market.pulse.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.market.pulse.model.Cart

@Dao
interface CartDao {

    @Query("SELECT * FROM carts WHERE userId = :userId LIMIT 1")
    suspend fun getCartByUserId(userId: Int): Cart?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: Cart)

    @Query("DELETE FROM carts WHERE id = :cartId")
    suspend fun deleteCart(cartId: Int)
}
