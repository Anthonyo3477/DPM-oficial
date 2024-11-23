package Activity;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import modules.Chat;

public class ChatViewModel extends ViewModel {

    private final MutableLiveData<List<Chat>> chats = new MutableLiveData<>();
    private List<Chat> allChats = new ArrayList<>();

    // Método para obtener los chats
    public LiveData<List<Chat>> getChats() {
        return chats;
    }

    // Método para cargar chats desde una fuente de datos (simulada)
    public void loadChats() {
        new Handler().postDelayed(() -> {
            allChats.add(new Chat("Chat 1", "Mensaje de prueba 1","","",""));
            allChats.add(new Chat("Chat 2", "Mensaje de prueba 2","","",""));
            allChats.add(new Chat("Chat 3", "Mensaje de prueba 3","","",""));

            chats.setValue(allChats);
        }, 1000);
    }

    // Método para filtrar los chats según el texto de búsqueda
    public void searchChats(String query) {
        if (query.isEmpty()) {
            chats.setValue(allChats);
        } else {
            List<Chat> filteredChats = new ArrayList<>();
            for (Chat chat : allChats) {
                if (chat.getNickName().toLowerCase().contains(query.toLowerCase())) {
                    filteredChats.add(chat);
                }
            }
            chats.setValue(filteredChats);
        }
    }
}
