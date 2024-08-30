package es.market.pulse.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import es.market.pulse.utils.Converters

@Entity(
    tableName = "orders",
    foreignKeys = [ForeignKey(
        entity = Address::class,
        parentColumns = ["id"],
        childColumns = ["shippingAddressId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["shippingAddressId"])] // Añadir índice aquí
)
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val userId: Int,
    val shippingAddressId: Int,
    val orderDate: Long,
    val status: String,
    @TypeConverters(Converters::class)
    val productList: List<Product>
)