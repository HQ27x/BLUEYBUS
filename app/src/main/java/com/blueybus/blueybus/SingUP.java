package com.blueybus.blueybus;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingUP extends AppCompatActivity {

    private static final String TAG = "SingUP";
    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signupButton;
    private TextView errorMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        signupButton = findViewById(R.id.signup_button);
        errorMessageTextView = findViewById(R.id.error_message);

        // Set onClickListener for signup button
        signupButton.setOnClickListener(view -> createAccount(emailEditText.getText().toString(), passwordEditText.getText().toString()));

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }

        // Create user with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            errorMessageTextView.setText("Falla de autenticación (TwT): " + task.getException().getMessage());
                            errorMessageTextView.setVisibility(View.VISIBLE);
                            updateUI(null);
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailEditText.getText().toString();
        if (email.isEmpty()) {
            emailEditText.setError("Requerido.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        String password = passwordEditText.getText().toString();
        if (password.isEmpty()) {
            passwordEditText.setError("Requerido.");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        String confirmPassword = confirmPasswordEditText.getText().toString();
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Confirma contraseña.");
            valid = false;
        } else {
            confirmPasswordEditText.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Redirect to another activity or show a success message
            errorMessageTextView.setText("Registrado correctamente");
            errorMessageTextView.setVisibility(View.VISIBLE);
        } else {
            // Clear the form or show an error message
        }
    }
}
