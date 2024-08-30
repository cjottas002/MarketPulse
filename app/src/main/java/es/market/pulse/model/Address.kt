package es.market.pulse.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class Address(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val street: String,
    val city: String,
    val postalCode: Int,
    val country: String,
)