package com.developer.test.chat.ui.activities.home;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u001aB=\u00126\u0010\u0003\u001a2\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0004\u00a2\u0006\u0002\u0010\fJ\b\u0010\u0012\u001a\u00020\tH\u0016J\u001c\u0010\u0013\u001a\u00020\u000b2\n\u0010\u0014\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\n\u001a\u00020\tH\u0016J\u001c\u0010\u0015\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\tH\u0016J\u0014\u0010\u0019\u001a\u00020\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010RA\u0010\u0003\u001a2\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/developer/test/chat/ui/activities/home/UserAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/developer/test/chat/ui/activities/home/UserAdapter$ConversionViewHolder;", "onClick", "Lkotlin/Function2;", "Landroid/view/View;", "Lkotlin/ParameterName;", "name", "view", "", "position", "", "(Lkotlin/jvm/functions/Function2;)V", "getOnClick", "()Lkotlin/jvm/functions/Function2;", "userList", "Ljava/util/ArrayList;", "Lcom/developer/test/chat/models/User;", "getItemCount", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setData", "ConversionViewHolder", "chat_debug"})
public final class UserAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.developer.test.chat.ui.activities.home.UserAdapter.ConversionViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function2<android.view.View, java.lang.Integer, kotlin.Unit> onClick = null;
    private java.util.ArrayList<com.developer.test.chat.models.User> userList;
    
    public UserAdapter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super android.view.View, ? super java.lang.Integer, kotlin.Unit> onClick) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.jvm.functions.Function2<android.view.View, java.lang.Integer, kotlin.Unit> getOnClick() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.developer.test.chat.ui.activities.home.UserAdapter.ConversionViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.developer.test.chat.ui.activities.home.UserAdapter.ConversionViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.developer.test.chat.models.User> userList) {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/developer/test/chat/ui/activities/home/UserAdapter$ConversionViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "mBinding", "Lcom/developer/test/chat/databinding/ItemConversationBinding;", "(Lcom/developer/test/chat/ui/activities/home/UserAdapter;Lcom/developer/test/chat/databinding/ItemConversationBinding;)V", "getMBinding", "()Lcom/developer/test/chat/databinding/ItemConversationBinding;", "chat_debug"})
    public final class ConversionViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.developer.test.chat.databinding.ItemConversationBinding mBinding = null;
        
        public ConversionViewHolder(@org.jetbrains.annotations.NotNull()
        com.developer.test.chat.databinding.ItemConversationBinding mBinding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.developer.test.chat.databinding.ItemConversationBinding getMBinding() {
            return null;
        }
    }
}