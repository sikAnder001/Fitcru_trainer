package com.developer.test.chat.ui.activities.home;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0016J\u001c\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\f\u001a\u00020\bH\u0016J\u001c\u0010\r\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0016J\u0014\u0010\u0011\u001a\u00020\n2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/developer/test/chat/ui/activities/home/StatusAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/developer/test/chat/ui/activities/home/StatusAdapter$StatusViewHolder;", "()V", "userStatuses", "Ljava/util/ArrayList;", "Lcom/developer/test/chat/ui/activities/home/UserStatus;", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setData", "StatusViewHolder", "chat_debug"})
public final class StatusAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.developer.test.chat.ui.activities.home.StatusAdapter.StatusViewHolder> {
    private java.util.ArrayList<com.developer.test.chat.ui.activities.home.UserStatus> userStatuses;
    
    public StatusAdapter() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.developer.test.chat.ui.activities.home.StatusAdapter.StatusViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.developer.test.chat.ui.activities.home.StatusAdapter.StatusViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.developer.test.chat.ui.activities.home.UserStatus> userStatuses) {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/developer/test/chat/ui/activities/home/StatusAdapter$StatusViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "mBinding", "Lcom/developer/test/chat/databinding/ItemStatusBinding;", "(Lcom/developer/test/chat/ui/activities/home/StatusAdapter;Lcom/developer/test/chat/databinding/ItemStatusBinding;)V", "getMBinding", "()Lcom/developer/test/chat/databinding/ItemStatusBinding;", "chat_debug"})
    public final class StatusViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.developer.test.chat.databinding.ItemStatusBinding mBinding = null;
        
        public StatusViewHolder(@org.jetbrains.annotations.NotNull()
        com.developer.test.chat.databinding.ItemStatusBinding mBinding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.developer.test.chat.databinding.ItemStatusBinding getMBinding() {
            return null;
        }
    }
}