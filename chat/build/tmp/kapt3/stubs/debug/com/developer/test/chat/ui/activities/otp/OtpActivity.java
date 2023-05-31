package com.developer.test.chat.ui.activities.otp;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\u0010\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0013H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/developer/test/chat/ui/activities/otp/OtpActivity;", "Lcom/developer/test/chat/utils/BaseActivity;", "()V", "countDownTimer", "Landroid/os/CountDownTimer;", "mAuth", "Lcom/google/firebase/auth/FirebaseAuth;", "mBinding", "Lcom/developer/test/chat/databinding/ActivityOtpBinding;", "getMBinding", "()Lcom/developer/test/chat/databinding/ActivityOtpBinding;", "mBinding$delegate", "Lkotlin/Lazy;", "phoneNumber", "", "resendToken", "Lcom/google/firebase/auth/PhoneAuthProvider$ForceResendingToken;", "verificationId", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "sendOtp", "signInWithPhoneAuthCredential", "credential", "Lcom/google/firebase/auth/PhoneAuthCredential;", "startCountdown", "chat_debug"})
public final class OtpActivity extends com.developer.test.chat.utils.BaseActivity {
    private android.os.CountDownTimer countDownTimer;
    private final kotlin.Lazy mBinding$delegate = null;
    private java.lang.String verificationId = "";
    private com.google.firebase.auth.FirebaseAuth mAuth;
    private java.lang.String phoneNumber = "";
    private com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken resendToken;
    
    public OtpActivity() {
        super();
    }
    
    private final com.developer.test.chat.databinding.ActivityOtpBinding getMBinding() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void sendOtp() {
    }
    
    private final void startCountdown() {
    }
    
    private final void signInWithPhoneAuthCredential(com.google.firebase.auth.PhoneAuthCredential credential) {
    }
}