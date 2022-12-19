package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivitySignInBinding;
import com.example.chatapp.databinding.ActivitySignUpBinding;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private String encodedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.textSignIn.setOnClickListener(v-> onBackPressed());
        binding.buttonSignUp.setOnClickListener(v->{
            if (isValidSignUpDetails())
                signUp();
        });
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }


    private void signUp(){

    }

//check valid input
    private Boolean isValidSignUpDetails(){
        if (encodedImage ==null) {
            showToast("Select profile image");
            return false;
        }else if (binding.inputName.getText().toString().trim().isEmpty()){
            showToast("Enter name");
            return false;
        }else if(binding.inputEmail.getText().toString().trim().isEmpty()){
            showToast("Enter email");
            return false;
        }else if (binding.inputPassword.getText().toString().trim().isEmpty()){
            showToast("Enter password");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()){
            showToast("Enter valid email");
            return false;
        }else if(binding.inputConfirmPassword.getText().toString().trim().isEmpty()){
            showToast("Confirm your password");
            return false;
        }else if (!binding.inputPassword.getText().toString().equals(binding.inputConfirmPassword.getText().toString())){
            showToast("Password and confirm password must be same");
            return false;
        }else {
            return true;
        }
    }


    private void loading(Boolean isLoading){
        if(isLoading){
            binding.buttonSignUp.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.buttonSignUp.setVisibility(View.VISIBLE);

        }
    }

}