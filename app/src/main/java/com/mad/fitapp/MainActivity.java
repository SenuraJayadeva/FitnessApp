package com.mad.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPassword;
    private Button loginButton;
    private TextView register;
    private ProgressDialog dialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Home.class));
        }

        loginUser();
    }

    private void loginUser() {
        userEmail = findViewById(R.id.login_email);
        userPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        register = findViewById(R.id.login_to_register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    userEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    userPassword.setError("Password is required");
                    return;
                }

                dialog.setMessage("Login in...");
                dialog.show();

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Email or Password is wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserRegistation.class));
            }
        });
    }
}