package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivitySignInBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners(){
        binding.textCreateNewAccount.setOnClickListener(v ->
            startActivity(new Intent(getApplicationContext(),SignUpActivity.class)));

//        test adding data
//        binding.buttonSignIn.setOnClickListener(v-> addDataToFirestore());

    }

    // test add data
    /*
    private void addDataToFirestore(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String,Object> data = new HashMap<>();
        data.put("first_name","Lac");
        data.put("last_name", "Viet");
        database.collection("users")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getApplicationContext(),"Data inserted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception-> {
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    */


}