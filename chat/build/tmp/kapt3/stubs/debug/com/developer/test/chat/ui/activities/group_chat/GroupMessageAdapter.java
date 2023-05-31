package com.developer.test.chat.ui.activities.group_chat;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u001e\u001fB=\u00126\u0010\u0003\u001a2\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0004\u00a2\u0006\u0002\u0010\fJ\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0013H\u0016J\u0018\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0013H\u0017J\u0018\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0013H\u0016J\u001e\u0010\u001c\u001a\u00020\u000b2\u0016\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u001dR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R>\u0010\u0003\u001a2\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/developer/test/chat/ui/activities/group_chat/GroupMessageAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "onClick", "Lkotlin/Function2;", "Landroid/view/View;", "Lkotlin/ParameterName;", "name", "view", "", "imageUrl", "", "(Lkotlin/jvm/functions/Function2;)V", "messageList", "Ljava/util/ArrayList;", "Lcom/developer/test/chat/ui/activities/chat/Message;", "reactionList", "", "getItemCount", "", "getItemViewType", "position", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setData", "Lkotlin/collections/ArrayList;", "ReceiveViewHolder", "SentViewHolder", "chat_debug"})
public final class GroupMessageAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    private final kotlin.jvm.functions.Function2<android.view.View, java.lang.String, kotlin.Unit> onClick = null;
    private java.util.ArrayList<com.developer.test.chat.ui.activities.chat.Message> messageList;
    private final int[] reactionList = null;
    
    public GroupMessageAdapter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super android.view.View, ? super java.lang.String, kotlin.Unit> onClick) {
        super();
    }
    
    @java.lang.Override()
    public int getItemViewType(int position) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"ClickableViewAccessibility"})
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.developer.test.chat.ui.activities.chat.Message> messageList) {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/developer/test/chat/ui/activities/group_chat/GroupMessageAdapter$SentViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "mBinding", "Lcom/developer/test/chat/databinding/ItemSentGroupBinding;", "(Lcom/developer/test/chat/ui/activities/group_chat/GroupMessageAdapter;Lcom/developer/test/chat/databinding/ItemSentGroupBinding;)V", "getMBinding", "()Lcom/developer/test/chat/databinding/ItemSentGroupBinding;", "chat_debug"})
    final class SentViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.developer.test.chat.databinding.ItemSentGroupBinding mBinding = null;
        
        public SentViewHolder(@org.jetbrains.annotations.NotNull()
        com.developer.test.chat.databinding.ItemSentGroupBinding mBinding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.developer.test.chat.databinding.ItemSentGroupBinding getMBinding() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/developer/test/chat/ui/activities/group_chat/GroupMessageAdapter$ReceiveViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "mBinding", "Lcom/developer/test/chat/databinding/ItemReceiveGroupBinding;", "(Lcom/developer/test/chat/ui/activities/group_chat/GroupMessageAdapter;Lcom/developer/test/chat/databinding/ItemReceiveGroupBinding;)V", "getMBinding", "()Lcom/developer/test/chat/databinding/ItemReceiveGroupBinding;", "chat_debug"})
    final class ReceiveViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.developer.test.chat.databinding.ItemReceiveGroupBinding mBinding = null;
        
        public ReceiveViewHolder(@org.jetbrains.annotations.NotNull()
        com.developer.test.chat.databinding.ItemReceiveGroupBinding mBinding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.developer.test.chat.databinding.ItemReceiveGroupBinding getMBinding() {
            return null;
        }
    }
}