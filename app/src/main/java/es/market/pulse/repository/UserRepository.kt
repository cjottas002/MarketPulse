package es.market.pulse.repository

import es.market.pulse.database.UserDao
import es.market.pulse.model.User

class UserRepository(private val userDao: UserDao) {

    fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    fun getUserById(userId: Int): User?{
        return userDao.getUserById(userId)
    }

    fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun addUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}