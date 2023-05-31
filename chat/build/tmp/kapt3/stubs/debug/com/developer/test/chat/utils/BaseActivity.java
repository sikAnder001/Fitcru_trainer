package com.developer.test.chat.utils;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"J1\u0010#\u001a\u00020 2\u0006\u0010$\u001a\u00020%2!\u0010&\u001a\u001d\u0012\u0013\u0012\u00110(\u00a2\u0006\f\b)\u0012\b\b*\u0012\u0004\b\b(+\u0012\u0004\u0012\u00020 0\'J9\u0010#\u001a\u00020 2\u0006\u0010$\u001a\u00020%2\u0006\u0010,\u001a\u00020%2!\u0010&\u001a\u001d\u0012\u0013\u0012\u00110(\u00a2\u0006\f\b)\u0012\b\b*\u0012\u0004\b\b(+\u0012\u0004\u0012\u00020 0\'JA\u0010#\u001a\u00020 2\u0006\u0010$\u001a\u00020%2\u0006\u0010,\u001a\u00020%2\u0006\u0010-\u001a\u00020%2!\u0010&\u001a\u001d\u0012\u0013\u0012\u00110(\u00a2\u0006\f\b)\u0012\b\b*\u0012\u0004\b\b(+\u0012\u0004\u0012\u00020 0\'J\u0006\u0010.\u001a\u00020 J\u0012\u0010/\u001a\u00020 2\b\u00100\u001a\u0004\u0018\u000101H\u0014J\b\u00102\u001a\u00020 H\u0014J\b\u00103\u001a\u00020 H\u0014J\u000e\u00104\u001a\u00020 2\u0006\u00105\u001a\u00020%R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u0010\u001a\u00020\u00118FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\n\u001a\u0004\b\u0012\u0010\u0013R\u001b\u0010\u0015\u001a\u00020\u00168FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0019\u0010\n\u001a\u0004\b\u0017\u0010\u0018R\u001b\u0010\u001a\u001a\u00020\u001b8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001e\u0010\n\u001a\u0004\b\u001c\u0010\u001d\u00a8\u00066"}, d2 = {"Lcom/developer/test/chat/utils/BaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "dialog", "Landroid/app/Dialog;", "firebaseAuth", "Lcom/google/firebase/auth/FirebaseAuth;", "getFirebaseAuth", "()Lcom/google/firebase/auth/FirebaseAuth;", "firebaseAuth$delegate", "Lkotlin/Lazy;", "firebaseDatabase", "Lcom/google/firebase/database/FirebaseDatabase;", "getFirebaseDatabase", "()Lcom/google/firebase/database/FirebaseDatabase;", "firebaseDatabase$delegate", "firebaseMessaging", "Lcom/google/firebase/messaging/FirebaseMessaging;", "getFirebaseMessaging", "()Lcom/google/firebase/messaging/FirebaseMessaging;", "firebaseMessaging$delegate", "firebaseRemoteConfig", "Lcom/google/firebase/remoteconfig/FirebaseRemoteConfig;", "getFirebaseRemoteConfig", "()Lcom/google/firebase/remoteconfig/FirebaseRemoteConfig;", "firebaseRemoteConfig$delegate", "firebaseStorage", "Lcom/google/firebase/storage/FirebaseStorage;", "getFirebaseStorage", "()Lcom/google/firebase/storage/FirebaseStorage;", "firebaseStorage$delegate", "getBackgroundFromFirebase", "", "view", "Landroid/view/View;", "getChildValues", "childPath1", "", "onDataChange", "Lkotlin/Function1;", "Lcom/google/firebase/database/DataSnapshot;", "Lkotlin/ParameterName;", "name", "snapshot", "childPath2", "childPath3", "hideProgressDialog", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onResume", "showProgressDialog", "message", "chat_debug"})
public class BaseActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.app.Dialog dialog;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy firebaseAuth$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy firebaseStorage$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy firebaseDatabase$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy firebaseMessaging$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy firebaseRemoteConfig$delegate = null;
    
    public BaseActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.firebase.auth.FirebaseAuth getFirebaseAuth() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.firebase.storage.FirebaseStorage getFirebaseStorage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.firebase.database.FirebaseDatabase getFirebaseDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.firebase.messaging.FirebaseMessaging getFirebaseMessaging() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.firebase.remoteconfig.FirebaseRemoteConfig getFirebaseRemoteConfig() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void getBackgroundFromFirebase(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    public final void showProgressDialog(@org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    public final void hideProgressDialog() {
    }
    
    public final void getChildValues(@org.jetbrains.annotations.NotNull()
    java.lang.String childPath1, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.google.firebase.database.DataSnapshot, kotlin.Unit> onDataChange) {
    }
    
    public final void getChildValues(@org.jetbrains.annotations.NotNull()
    java.lang.String childPath1, @org.jetbrains.annotations.NotNull()
    java.lang.String childPath2, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.google.firebase.database.DataSnapshot, kotlin.Unit> onDataChange) {
    }
    
    public final void getChildValues(@org.jetbrains.annotations.NotNull()
    java.lang.String childPath1, @org.jetbrains.annotations.NotNull()
    java.lang.String childPath2, @org.jetbrains.annotations.NotNull()
    java.lang.String childPath3, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.google.firebase.database.DataSnapshot, kotlin.Unit> onDataChange) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
}