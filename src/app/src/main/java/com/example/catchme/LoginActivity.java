package com.example.catchme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText ETMail, ETPass;
    private TextView btnSignUp, btnForgot;
    private Button btnLogin;
    private ImageView btnGoogle, btnFacebook;
    private ProgressBar pgBar;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignUp = findViewById(R.id.loginSignUp);
        btnForgot = findViewById(R.id.loginForgot);
        btnLogin = findViewById(R.id.loginButton);
        btnGoogle = findViewById(R.id.imgVGoogle);
        btnFacebook = findViewById(R.id.imgVFacebook);

        progressDialog = new ProgressDialog(this);

        ETMail = findViewById(R.id.loginMail);
        ETPass = findViewById(R.id.loginPass);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        pgBar = findViewById(R.id.loginProgressBar);


        
        SetOnClickListener();
    }

    void SetOnClickListener(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected()) {
                    showConnectedDialog();
                    return;
                }
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected()) {
                    showConnectedDialog();
                    return;
                }
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected()) {
                    showConnectedDialog();
                    return;
                }
                loginUSer();
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected()) {
                    showConnectedDialog();
                    return;
                }
                loginGoogle();
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected()) {
                    showConnectedDialog();
                    return;
                }
                loginFacebook();
            }
        });




    }

    void loginUSer(){
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            FirebaseAuth.getInstance().signOut();
            currentUser = null;
        }

        String mail = ETMail.getText().toString().trim();
        String pass = ETPass.getText().toString().trim();

        if (mail.isEmpty()){
            ETMail.setError("Mail is required!");
            ETMail.requestFocus();
            return;
        }

        if (pass.isEmpty()){
            ETPass.setError("Password is required!");
            ETPass.requestFocus();
            return;
        }

        pgBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_LONG).show();
                            pgBar.setVisibility(View.GONE);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to login! Try again!", Toast.LENGTH_LONG).show();
                            pgBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    void loginGoogle(){
        Toast.makeText(LoginActivity.this, "Not available now!", Toast.LENGTH_LONG).show();
    }

    void loginFacebook(){
        Toast.makeText(LoginActivity.this, "Not available now!", Toast.LENGTH_LONG).show();
    }

    // CONNECT
    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())){
            return true;
        }else{
            return false;
        }
    }
    public void showConnectedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Please connect to the internet or use the offline login")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}