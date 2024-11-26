package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dmp.R;

import java.util.List;

import modules.Chat;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<Chat> chatList;
    private final OnChatClickListener listener;

    public ChatAdapter(List<Chat> chatList, OnChatClickListener listener) {
        this.chatList = chatList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.nickNameTextView.setText(chat.getNickName());
        holder.lastMessageTextView.setText(chat.getLastMessage());

        holder.itemView.setOnClickListener(v -> listener.onEditChat(chat));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onDeleteChat(chat);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void updateChats(List<Chat> chats) {
        this.chatList.clear();
        this.chatList.addAll(chats);
        notifyDataSetChanged();
    }

    public interface OnChatClickListener {
        void onEditChat(Chat chat);
        void onDeleteChat(Chat chat);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView nickNameTextView, lastMessageTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            nickNameTextView = itemView.findViewById(R.id.Nickname);
            lastMessageTextView = itemView.findViewById(R.id.lastMessage);
        }
    }
}