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

    private List<Chat> chatList;

    // Constructor para recibir la lista de chats
    public ChatAdapter(List<Chat> chatList) {
        this.chatList = chatList;
    }

    // Crea el ViewHolder para cada elemento de la lista
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    // Asocia los datos de cada chat a las vistas correspondientes en el ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.bind(chat);
    }

    // Retorna el número total de chats en la lista
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    // Método para actualizar la lista de chats en el adaptador
    public void updateChats(Object newChatList) {
        chatList = (List<Chat>) newChatList;
        notifyDataSetChanged();
    }

    // ViewHolder que mantiene las vistas de cada chat
    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        private final TextView chatTitle;
        private final TextView lastMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);
            chatTitle = itemView.findViewById(R.id.chatTitle);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }

        public void bind(Chat chat) {
            chatTitle.setText(chat.getTitle());
            lastMessage.setText(chat.getLastMessage());
        }
    }
}
