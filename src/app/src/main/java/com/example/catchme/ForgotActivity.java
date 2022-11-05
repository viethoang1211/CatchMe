package com.example.catchme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {
    private EditText ETMail;
    private Button btnReset;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        mAuth = FirebaseAuth.getInstance();
        ETMail = findViewById(R.id.forgotMail);
        btnReset = findViewById(R.id.forgotBtn);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
            }
        });
    }

    private void resetPass(){
        String mail = ETMail.getText().toString().trim();

        if (mail.isEmpty()){
            ETMail.setError("Mail is required!");
            ETMail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            ETMail.setError("Please provide valid mail!");
            ETMail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotActivity.this, "Email sent!", Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            Toast.makeText(ForgotActivity.this, "Failed to sent email!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}