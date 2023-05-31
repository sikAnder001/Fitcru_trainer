package com.developer.test.chat.utils;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tJ\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0012\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\tJ\u000e\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\fJ\u000e\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/developer/test/chat/utils/PrefUtils;", "", "()V", "sharedPreferences", "Landroid/content/SharedPreferences;", "clear", "", "get", "key", "", "defaultValue", "getUser", "Lcom/developer/test/chat/models/User;", "getUserLogged", "", "init", "context", "Landroid/content/Context;", "set", "value", "setUser", "user", "setUserLogged", "chat_debug"})
public final class PrefUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.developer.test.chat.utils.PrefUtils INSTANCE = null;
    private static android.content.SharedPreferences sharedPreferences;
    
    private PrefUtils() {
        super();
    }
    
    public final void init(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final boolean getUserLogged() {
        return false;
    }
    
    public final void setUserLogged(boolean value) {
    }
    
    public final void setUser(@org.jetbrains.annotations.NotNull()
    com.developer.test.chat.models.User user) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.developer.test.chat.models.User getUser() {
        return null;
    }
    
    public final void set(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void get(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    java.lang.String defaultValue) {
    }
    
    public final void clear() {
    }
}