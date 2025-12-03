package com.daviddai.assignment3_33906211.data.genAi

import android.content.Context
import com.daviddai.assignment3_33906211.data.user.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AiResponseRepository(context: Context) {
    private val aiResponseDao = UserDatabase.getDatabase(context).aiResponseDao()

    suspend fun insert(aiResponse: AiResponse) {
        withContext(Dispatchers.IO) {
            aiResponseDao.insert(aiResponse)
        }
    }

    suspend fun deleteByUserId(userId: String) {
        withContext(Dispatchers.IO) {
            aiResponseDao.deleteByUserId(userId)
        }
    }

    suspend fun getAiResponsesByUserId(userId: String): Flow<List<AiResponse>>? {
        return withContext(Dispatchers.IO) {
            aiResponseDao.getAiResponsesByUserId(userId)
        }
    }

}