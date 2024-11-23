package Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dmp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Activity.ChatViewModel;
import adapters.ChatAdapter;

public class menuApp extends AppCompatActivity {

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
        chatAdapter = new ChatAdapter(new ArrayList<>());
        recyclerViewChats.setAdapter(chatAdapter);

        // Inicializar el ViewModel
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        // Observar los cambios en los datos de los chats
        chatViewModel.getChats().observe(this, chats -> {
            chatAdapter.updateChats(chats);
        });

        // Configurar la barra de búsqueda
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                chatViewModel.searchChats(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Acción al presionar el botón de nuevo chat
        fabNewChat.setOnClickListener(view -> {
            Toast.makeText(menuApp.this, "Nuevo chat", Toast.LENGTH_SHORT).show();
        });

        // Cargar chats inicialmente
        chatViewModel.loadChats();
    }
}
