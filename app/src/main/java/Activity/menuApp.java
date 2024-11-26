package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dmp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adapters.ChatAdapter;
import modules.User;
import modules.Chat;

public class menuApp extends AppCompatActivity implements ChatAdapter.OnChatClickListener { // error

    private RecyclerView recyclerViewChats;
    private ChatAdapter chatAdapter;
    private TextInputEditText searchInput;
    private FloatingActionButton fabNewChat;
    private ChatViewModel chatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_app);

        // Inicializar vistas
        recyclerViewChats = findViewById(R.id.recyclerViewChats);
        searchInput = findViewById(R.id.searchInput);
        fabNewChat = findViewById(R.id.fabNewChat);

        // Inicializar el RecyclerView
        recyclerViewChats.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(new ArrayList<>(), this); // Pasar this como listener
        recyclerViewChats.setAdapter(chatAdapter);

        // Inicializar el ViewModel
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        // Observar los cambios en los datos de los chats
        chatViewModel.getUsers().observe(this, users -> {
            List<Chat> chats = convertUsersToChats(users);
            chatAdapter.updateChats(chats);
        });

        // Configurar la barra de búsqueda
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                chatViewModel.searchUsers(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Acción al presionar el botón de nuevo chat
        fabNewChat.setOnClickListener(view -> {
            Intent intent = new Intent(menuApp.this, NuevoChat.class);
            startActivity(intent);
            Toast.makeText(menuApp.this, "Nuevo chat", Toast.LENGTH_SHORT).show();
        });

        // Cargar los usuarios
        chatViewModel.loadUsers();
    }

    // Método para convertir User a Chat
    private List<Chat> convertUsersToChats(List<User> users) {
        List<Chat> chats = new ArrayList<>();
        for (User user : users) {
            Chat chat = new Chat();
            chat.setNickName(user.getNickname());
            chats.add(chat);
        }
        return chats;
    }

    // Implementación de los callbacks del adaptador
    @Override // error
    public void onEditChat(Chat chat) {
        Toast.makeText(this, "Editar chat: " + chat.getNickName(), Toast.LENGTH_SHORT).show();
        // Agregar lógica para editar
    }

    @Override // error
    public void onDeleteChat(Chat chat) {
        Toast.makeText(this, "Eliminar chat: " + chat.getNickName(), Toast.LENGTH_SHORT).show();
        // Agregar lógica para eliminar
    }
}