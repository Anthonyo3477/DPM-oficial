package Activity;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import modules.User;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends AndroidViewModel {

    private MutableLiveData<List<User>> filteredUsers = new MutableLiveData<>();
    private List<User> allUsers = new ArrayList<>();
    private DatabaseReference mDatabase;
    private ValueEventListener valueEventListener;

    public ChatViewModel(Application application) {
        super(application);
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios");
        loadUsers();
    }

    public LiveData<List<User>> getUsers() {
        return filteredUsers;
    }

    // Cargar usuarios desde Firebase solo una vez
    public void loadUsers() {
        if (valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    allUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            allUsers.add(user);
                        }
                    }
                    filteredUsers.setValue(new ArrayList<>(allUsers));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    filteredUsers.setValue(new ArrayList<>());
                }
            };
        }
        mDatabase.addValueEventListener(valueEventListener);
    }

    // Método para eliminar el listener cuando ya no es necesario (por ejemplo, cuando la actividad se destruye)
    public void removeListener() {
        if (valueEventListener != null) {
            mDatabase.removeEventListener(valueEventListener);
            valueEventListener = null;
        }
    }

    // Método de búsqueda de usuarios según el query
    public void searchUsers(String query) {
        List<User> results = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getNickname().toLowerCase().contains(query.toLowerCase())) {
                results.add(user);
            }
        }
        filteredUsers.setValue(results);
    }
}
