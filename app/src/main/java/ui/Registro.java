package ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dmp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import modules.User;

public class Registro extends AppCompatActivity {

    private TextInputEditText txtCorreo, txtContraseña, txtConfirmarContraseña, txtNombreUsuario;
    private MaterialButton btnRegistrar, btnVolver;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);

        // Inicializar vistas
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContraseña = findViewById(R.id.txtContraseña);
        txtConfirmarContraseña = findViewById(R.id.txtConfirmarContraseña);
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario); // Campo de nombre de usuario
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);

        // Inicializar FirebaseAuth y Realtime Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios");

        // Configurar botón de registro
        btnRegistrar.setOnClickListener(v -> registrarUsuario());

        // Configurar botón de volver
        btnVolver.setOnClickListener(v -> finish());
    }

    private void registrarUsuario() {
        String correo = txtCorreo.getText().toString().trim();
        String nombreUsuario = txtNombreUsuario.getText().toString().trim();
        String contraseña = txtContraseña.getText().toString();
        String confirmarContraseña = txtConfirmarContraseña.getText().toString();

        // Validaciones
        if (TextUtils.isEmpty(correo)) {
            txtCorreo.setError("El correo es obligatorio");
            txtCorreo.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            txtCorreo.setError("Ingrese un correo válido");
            txtCorreo.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(nombreUsuario)) {
            txtNombreUsuario.setError("El nombre de usuario es obligatorio");
            txtNombreUsuario.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(contraseña)) {
            txtContraseña.setError("La contraseña es obligatoria");
            txtContraseña.requestFocus();
            return;
        }

        if (contraseña.length() < 6) {
            txtContraseña.setError("La contraseña debe tener al menos 6 caracteres");
            txtContraseña.requestFocus();
            return;
        }

        if (!contraseña.equals(confirmarContraseña)) {
            txtConfirmarContraseña.setError("Las contraseñas no coinciden");
            txtConfirmarContraseña.requestFocus();
            return;
        }

        // Registrar el usuario con Firebase Authentication
        mAuth.createUserWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Obtener el ID del usuario autenticado
                        String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : "";
                        if (!userId.isEmpty()) {
                            User nuevoUsuario = new User( userId, correo, contraseña, nombreUsuario );

                            // Guardar datos del usuario en la base de datos
                            mDatabase.child(userId).setValue(nuevoUsuario)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            // Registro exitoso
                                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Registro.this, Login.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        // Manejo de errores en caso de fallo de autenticación
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Error desconocido";
                        Toast.makeText(this, "Error de registro: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }
}