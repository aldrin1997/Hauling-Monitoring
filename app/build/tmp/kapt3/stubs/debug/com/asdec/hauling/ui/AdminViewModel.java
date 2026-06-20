package com.asdec.hauling.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u0019R+\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR+\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\r\u001a\u0004\b\u000f\u0010\t\"\u0004\b\u0010\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R+\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\r\u001a\u0004\b\u0013\u0010\t\"\u0004\b\u0014\u0010\u000b\u00a8\u0006\u001a"}, d2 = {"Lcom/asdec/hauling/ui/AdminViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/asdec/hauling/data/HaulingRepository;", "(Lcom/asdec/hauling/data/HaulingRepository;)V", "<set-?>", "", "newPassword", "getNewPassword", "()Ljava/lang/String;", "setNewPassword", "(Ljava/lang/String;)V", "newPassword$delegate", "Landroidx/compose/runtime/MutableState;", "newUsername", "getNewUsername", "setNewUsername", "newUsername$delegate", "selectedRole", "getSelectedRole", "setSelectedRole", "selectedRole$delegate", "createAccount", "", "onSuccess", "Lkotlin/Function0;", "app_debug"})
public final class AdminViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.asdec.hauling.data.HaulingRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState newUsername$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState newPassword$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState selectedRole$delegate = null;
    
    public AdminViewModel(@org.jetbrains.annotations.NotNull()
    com.asdec.hauling.data.HaulingRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNewUsername() {
        return null;
    }
    
    public final void setNewUsername(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNewPassword() {
        return null;
    }
    
    public final void setNewPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSelectedRole() {
        return null;
    }
    
    public final void setSelectedRole(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    /**
     * Creates a new user account.
     * Generates a unique UUID to satisfy the User data class requirements.
     */
    public final void createAccount(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
}