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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Login extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Por favor ingrese correo electrónico y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, task -> {
                            if (task.isSuccessful()) {
                                // Inicio de sesión exitoso
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Login.this, "INICIO DE SESIÓN EXITOSO :D", Toast.LENGTH_SHORT).show();
                                // Ir a la actividad FLRegistro
                                Intent intent = new Intent(Login.this, Menuuser.class);
                                startActivity(intent);
                                finish(); // Opcional: Llama a finish() para cerrar la actividad actual
                            } else {
                                // Si el inicio de sesión falla
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(Login.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                    Toast.makeText(Login.this, "Correo no registrado", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "ERROR AL INICIAR SESIÓN", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    public void registr (View vista){
        Intent miIntent2 = new Intent(this, Registrar.class);
        startActivity(miIntent2);
    }
}