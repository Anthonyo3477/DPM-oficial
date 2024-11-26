package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dmp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import modules.Chat;

public class NuevoChat extends AppCompatActivity {

    private TextInputEditText etChatName, etEmail, etPhoneNumber;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_chat);

        // Inicializando los campos de entrada
        etChatName = findViewById(R.id.etChatName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        // Inicializa Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference("chats");

        // Configuramos los botones para crear o cancelar el chat
        findViewById(R.id.btnCreateChat).setOnClickListener(this::onCreateChat);
        findViewById(R.id.btnCancel).setOnClickListener(view -> finish());
    }

    // Método para crear un nuevo chat
    private void onCreateChat(View view) {
        String chatName = etChatName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();

        // Validaciones para asegurarse de que los campos no estén vacíos
        if (TextUtils.isEmpty(chatName)) {
            Toast.makeText(this, "Por favor, ingresa un nombre para el chat", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, ingresa un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phoneNumber) || !Patterns.PHONE.matcher(phoneNumber).matches()) {
            Toast.makeText(this, "Por favor, ingresa un número telefónico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generar un ID único para el chat
        String chatId = mDatabase.push().getKey();
        if (chatId != null) {
            // Crear una nueva instancia de Chat
            Chat newChat = new Chat(chatId, chatName, "No messages yet", email, phoneNumber);

            // Guardar el chat en Firebase
            mDatabase.child(chatId).setValue(newChat)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Chat creado: " + chatName, Toast.LENGTH_SHORT).show();

                            // Crear un Intent para devolver el nombre y la información del chat
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("chatName", chatName);
                            resultIntent.putExtra("email", email);
                            resultIntent.putExtra("phoneNumber", phoneNumber);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        } else {
                            // Si hay un error al crear el chat, mostramos un mensaje de error
                            Toast.makeText(this, "Error al crear el chat", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}