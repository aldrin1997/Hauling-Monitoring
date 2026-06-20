package com.asdec.hauling.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    /**
     * Creates a new user.
     * Changed to REPLACE so that seeding the admin user doesn't crash
     * the app if it already exists during database creation.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: User)

    /**
     * Retrieves all users.
     * Useful for an Admin list view to see everyone registered.
     */
    @Query("SELECT * FROM users ORDER BY username ASC")
    fun getAllUsers(): Flow<List<User>>

    /**
     * Finds a specific user by username.
     * This is the exact function used by your Repository for Login logic.
     */
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    /**
     * Updates user information (e.g., changing a password or role).
     */
    @Update
    suspend fun updateUser(user: User)

    /**
     * Deletes a user account.
     */
    @Delete
    suspend fun deleteUser(user: User)

    /**
     * Delete by username specifically.
     */
    @Query("DELETE FROM users WHERE username = :username")
    suspend fun deleteByUsername(username: String)
}