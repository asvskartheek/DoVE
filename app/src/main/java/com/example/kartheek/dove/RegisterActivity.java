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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        final TextInputLayout mName = findViewById(R.id.reg_name);
        final TextInputLayout mPassword = findViewById(R.id.reg_password);
        final TextInputLayout mPhone = findViewById(R.id.reg_phone);
        final TextInputLayout mEmail = findViewById(R.id.reg_email);
        final TextInputLayout mYear = findViewById(R.id.reg_year);

        Button mRegBtn = findViewById(R.id.reg_reg_btn);
        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getEditText().getText().toString();
                String pass = mPassword.getEditText().getText().toString();
                String phone = mPhone.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String year = mYear.getEditText().getText().toString();
                createUser(name,pass,phone,email,year);
            }
        });

    }

    private void createUser(final String name, String pass, final String phone, final String email, final String year) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference usersRef = database.getReference("Users");
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = currentUser.getUid();

                            HashMap<String,String> userMap = new HashMap<>();
                            userMap.put("name",name);
                            userMap.put("phone",phone);
                            userMap.put("email",email);
                            userMap.put("year",year);
                            usersRef.child(uid).setValue(userMap);
                            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
