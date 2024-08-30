package es.market.pulse.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.market.pulse.model.CartItem

@Dao
interface CartItemDao {

    @Query("SELECT * FROM cart_items WHERE cartId = :cartId")
    suspend fun getItemsByCartId(cartId: Int): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_items WHERE id = :cartItemId")
    suspend fun deleteCartItem(cartItemId: Int)
}
