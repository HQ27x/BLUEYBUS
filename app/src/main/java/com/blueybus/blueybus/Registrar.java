package com.blueybus.blueybus;

import android.content.Intent;  // Import necesario para las Intents
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class Registrar extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Registrar.this, "Por favor ingrese correo electrónico y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registrar.this, task -> {
                            if (task.isSuccessful()) {
                                // Registro exitoso
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Registrar.this, "REGISTRO COMPLETO :D", Toast.LENGTH_SHORT).show();
                                // Ir a la actividad FLRegistro
                                Intent intent = new Intent(Registrar.this, FLRegistro.class);
                                startActivity(intent);
                                finish(); // Opcional: Llama a finish() para cerrar la actividad actual
                            } else {
                                // Si el registro falla
                                if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                    Toast.makeText(Registrar.this, "Contraseña débil", Toast.LENGTH_SHORT).show();
                                } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(Registrar.this, "Correo inválido", Toast.LENGTH_SHORT).show();
                                } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(Registrar.this, "Usuario existente", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Registrar.this, "ERROR AL REGISTRAR", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
