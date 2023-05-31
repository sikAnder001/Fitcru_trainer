package com.developer.test.chat.ui.activities.chat;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\u0018\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0017H\u0002J\u0010\u0010 \u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u0012H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/developer/test/chat/ui/activities/chat/ChatActivity;", "Lcom/developer/test/chat/utils/BaseActivity;", "()V", "currentUser", "Lcom/developer/test/chat/models/User;", "mBinding", "Lcom/developer/test/chat/databinding/ActivityChatBinding;", "getMBinding", "()Lcom/developer/test/chat/databinding/ActivityChatBinding;", "mBinding$delegate", "Lkotlin/Lazy;", "messageAdapter", "Lcom/developer/test/chat/ui/activities/chat/MessageAdapter;", "getMessageAdapter", "()Lcom/developer/test/chat/ui/activities/chat/MessageAdapter;", "messageAdapter$delegate", "messageList", "Ljava/util/ArrayList;", "Lcom/developer/test/chat/ui/activities/chat/Message;", "receiverRoom", "", "receiverUser", "resultUri", "Landroid/net/Uri;", "senderRoom", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "sendImageMessage", "message", "imageUri", "sendTextMessage", "chat_debug"})
public final class ChatActivity extends com.developer.test.chat.utils.BaseActivity {
    private final kotlin.Lazy mBinding$delegate = null;
    private final kotlin.Lazy messageAdapter$delegate = null;
    private final java.util.ArrayList<com.developer.test.chat.ui.activities.chat.Message> messageList = null;
    private java.lang.String senderRoom = "";
    private java.lang.String receiverRoom = "";
    private com.developer.test.chat.models.User currentUser;
    private com.developer.test.chat.models.User receiverUser;
    private android.net.Uri resultUri;
    
    public ChatActivity() {
        super();
    }
    
    private final com.developer.test.chat.databinding.ActivityChatBinding getMBinding() {
        return null;
    }
    
    private final com.developer.test.chat.ui.activities.chat.MessageAdapter getMessageAdapter() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void sendImageMessage(com.developer.test.chat.ui.activities.chat.Message message, android.net.Uri imageUri) {
    }
    
    private final void sendTextMessage(com.developer.test.chat.ui.activities.chat.Message message) {
    }
}