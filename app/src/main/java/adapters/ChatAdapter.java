package adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Activity.EditarChat;
import com.example.dmp.R;

import java.util.List;

import modules.Chat;
import android.app.Activity;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Chat> chatList;
    private Activity activity;

    // Constructor para recibir la lista de chats y la actividad actual
    public ChatAdapter(List<Chat> chatList) {
        this.chatList = chatList;
        this.activity = activity;
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
    public class ChatViewHolder extends RecyclerView.ViewHolder {

        private final TextView chatTitle;
        private final TextView lastMessage;
        private final Button deleteButton;
        private final Button otherButton;

        public ChatViewHolder(View itemView) {
            super(itemView);
            chatTitle = itemView.findViewById(R.id.chatTitle);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            otherButton = itemView.findViewById(R.id.otherButton);

            // Acción para el botón de eliminar
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Chat chat = chatList.get(position);
                        chatList.remove(position);
                        notifyItemRemoved(position);

                        // Usar la actividad para el Toast
                        if (activity != null) {
                            Toast.makeText(activity, "Chat eliminado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(itemView.getContext(), "Chat eliminado", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            // Acción para el botón de editar
            otherButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Chat chat = chatList.get(position);

                        // Asegúrate de que el contexto sea el correcto
                        Intent intent = new Intent(itemView.getContext(), EditarChat.class);
                        intent.putExtra("chat_id", chat.getId()); // Pasa el ID del chat para usarlo en la nueva actividad

                        // Usar el contexto adecuado para iniciar la actividad
                        if (activity != null) {
                            activity.startActivity(intent); // Si tienes una actividad, úsala
                        } else {
                            itemView.getContext().startActivity(intent); // Si no, usa el contexto del itemView
                        }
                    }
                }
            });
        }

        // Enlaza los datos de cada chat con la vista
        public void bind(Chat chat) {
            chatTitle.setText(chat.getNickName());
            lastMessage.setText(chat.getLastMessage());
        }
    }
}