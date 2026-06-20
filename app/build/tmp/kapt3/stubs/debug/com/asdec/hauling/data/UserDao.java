package com.asdec.hauling.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\rH\'J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/asdec/hauling/data/UserDao;", "", "createUser", "", "user", "Lcom/asdec/hauling/data/User;", "(Lcom/asdec/hauling/data/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteByUsername", "username", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteUser", "getAllUsers", "Lkotlinx/coroutines/flow/Flow;", "", "getUserByUsername", "updateUser", "app_debug"})
@androidx.room.Dao()
public abstract interface UserDao {
    
    /**
     * Creates a new user.
     * Changed to REPLACE so that seeding the admin user doesn't crash
     * the app if it already exists during database creation.
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createUser(@org.jetbrains.annotations.NotNull()
    com.asdec.hauling.data.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Retrieves all users.
     * Useful for an Admin list view to see everyone registered.
     */
    @androidx.room.Query(value = "SELECT * FROM users ORDER BY username ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.asdec.hauling.data.User>> getAllUsers();
    
    /**
     * Finds a specific user by username.
     * This is the exact function used by your Repository for Login logic.
     */
    @androidx.room.Query(value = "SELECT * FROM users WHERE username = :username LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserByUsername(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.asdec.hauling.data.User> $completion);
    
    /**
     * Updates user information (e.g., changing a password or role).
     */
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateUser(@org.jetbrains.annotations.NotNull()
    com.asdec.hauling.data.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Deletes a user account.
     */
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteUser(@org.jetbrains.annotations.NotNull()
    com.asdec.hauling.data.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Delete by username specifically.
     */
    @androidx.room.Query(value = "DELETE FROM users WHERE username = :username")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteByUsername(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}