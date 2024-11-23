package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dmp.R;
import com.google.android.material.button.MaterialButton;

import Activity.MainActivity;
import Activity.menuApp;

public class Login extends AppCompatActivity {

    private MaterialButton btnIniciarSesion, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingresar);

        // Inicializar los botones
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnVolver = findViewById(R.id.btnVolver);

        // Configurar el botón "Iniciar Sesión"
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, menuApp.class);
                startActivity(intent);
            }
        });

        // Configurar el botón "Volver"
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
