package com.example.kartheek.dove;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        final TextInputLayout mName = findViewById(R.id.login_name);
        final TextInputLayout mPassword = findViewById(R.id.login_password);

        Button mLoginBtn = findViewById(R.id.login_login_btn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection ConstantConditions
                String name = mName.getEditText().getText().toString();
                //noinspection ConstantConditions
                String pass = mPassword.getEditText().getText().toString();

                signIn(name,pass);
            }
        });
    }

    private void signIn(String name, String pass){
        mAuth.signInWithEmailAndPassword(name, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    "Successfully Signed in",
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this,
                                    "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
