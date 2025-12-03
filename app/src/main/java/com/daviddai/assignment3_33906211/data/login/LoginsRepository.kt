package com.daviddai.assignment3_33906211.data.login

import android.content.Context
import com.daviddai.assignment3_33906211.data.user.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * This is the repository class for the login.
 */
class LoginsRepository(context: Context) {

    // Property to hold the LoginDao instance.
    var loginDao: LoginDao = UserDatabase.getDatabase(context).loginDao()

    /**
     * insert new login into the database.
     */
    suspend fun insert(login: Login) = withContext(Dispatchers.IO) {
        loginDao.insert(login)
    }

    /**
     * update new login into the database.
     */
    suspend fun update(login: Login) = withContext(Dispatchers.IO) {
        loginDao.update(login)
    }

    /**
     * delete login from the database.
     */
    suspend fun delete(login: Login) = withContext(Dispatchers.IO) {
        loginDao.delete(login)
    }

    /**
     * Get logins by ID from the database.
     */
    suspend fun getLoginById(id: String): Login? = withContext(Dispatchers.IO) {
        loginDao.getLoginById(id)
    }

    /**
     * Delete logins by ID from the database.
     */
    suspend fun deleteLoginById(id: String) = withContext(Dispatchers.IO) {
        loginDao.deleteLoginById(id)
    }
}