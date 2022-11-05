package com.example.catchme;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText ETFullName, ETPass, ETPass2, ETMail;
    TextView btnAlready;
    Button btnReg;
    ProgressBar pgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btnAlready = findViewById(R.id.regAlreadyHaveAcc);

        ETFullName = findViewById(R.id.regFullname);
        ETMail = findViewById(R.id.regMail);
        ETPass = findViewById(R.id.regPass);
        ETPass2 = findViewById(R.id.regPass2);

        btnReg = findViewById(R.id.regBtnReg);

        pgBar = findViewById(R.id.progressBar);

        SetOnClickListener();
    }

    private void SetOnClickListener() {
        btnAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUSer();
            }
        });
    }

    private void registerUSer(){
        String username = ETFullName.getText().toString().trim();
        String mail = ETMail.getText().toString().trim();
        String pass = ETPass.getText().toString().trim();
        String pass2 = ETPass2.getText().toString().trim();

        if (username.isEmpty()){
            ETFullName.setError("Full name is required!");
            ETFullName.requestFocus();
            return;
        }

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

        if (pass.isEmpty()){
            ETPass.setError("Password is required!");
            ETPass.requestFocus();
            return;
        }

        if (pass.length() < 6){
            ETPass.setError("Min password length should be 6!");
            ETPass.requestFocus();
            return;
        }

        if (!pass2.equals(pass)){
            ETPass2.setError("Wrong password!");
            ETPass2.requestFocus();
            return;
        }

        pgBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User curUser = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(), username, mail, "***", LocalDate.now());
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(curUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                // update display name
                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(username).build();
                                                mAuth.getCurrentUser().updateProfile(profileUpdates);

                                                Toast.makeText(RegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                                pgBar.setVisibility(View.GONE);
                                                // Back to login
                                                finish();
                                            }else{
                                                Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                pgBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            pgBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}