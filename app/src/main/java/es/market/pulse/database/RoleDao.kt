package es.market.pulse.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.market.pulse.model.Role

@Dao
interface RoleDao {

    @Query("SELECT * FROM roles WHERE roleName = :roleName LIMIT 1")
    suspend fun getRoleByName(roleName: String): Role

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRole(role: Role)

    @Query("SELECT * FROM roles")
    suspend fun getAllRoles(): List<Role>
}
