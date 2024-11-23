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

public class Registro extends AppCompatActivity {

    private TextInputEditText txtCorreo, txtContraseña, txtConfirmarContraseña;
    private MaterialButton btnRegistrar, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);

        // Inicializar vistas
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContraseña = findViewById(R.id.txtContraseña);
        txtConfirmarContraseña = findViewById(R.id.txtConfirmarContraseña);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);

        // Configurar botón de registro
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        // Configurar botón de volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volver a la actividad anterior
                finish();
            }
        });
    }

    private void registrarUsuario() {
        String correo = txtCorreo.getText().toString().trim();
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

        // Simulación de registro exitoso (puedes integrar con una base de datos aquí)
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();

        // Redirigir al usuario a la ventana de inicio de sesión o menú principal
        Intent intent = new Intent(Registro.this, Login.class);
        startActivity(intent);
        finish();
    }
}
