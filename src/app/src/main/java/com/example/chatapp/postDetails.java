package com.example.chatapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.chatapp.Class.Post;
import com.example.chatapp.databinding.FragmentPostDetailsBinding;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class postDetails extends Fragment {

    private FragmentPostDetailsBinding binding;
    private Post thisPost;
    private FirebaseFirestore database;
    private PreferenceManager preferenceManager;
    private Boolean valid = false;
    private Boolean isLiked= false;


    public static postDetails newInstance() {
        return new postDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        database=FirebaseFirestore.getInstance();
        validUser();

        setListeners();



        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((fragment_container)getActivity()).setup(toolbar);
        TextView title  = view.findViewById(R.id.title);
        title.setText("Post");
        TextView test = view.findViewById(R.id.post_details_dur);
        Post post = (Post) getArguments().getSerializable("testing");
        test.setText(post.getUser_name());

        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(PostDetailsViewModel.class);
//        // TODO: Use the ViewModel
//    }

    private void setListeners(){
        binding.hidePost.setOnClickListener(v->{
            if (isValidUser()){
                hidePost();
            }
        });
        binding.deletePost.setOnClickListener(v->{
            if (isValidUser()){
                deletePost();
            }
        });
        
        binding.postDetailsLike.setOnClickListener(v->{
            database.collection(Constants.KEY_COLLECTION_LIKE).whereEqualTo(Constants.KEY_POST_ID,thisPost.id)
                    .whereEqualTo(Constants.KEY_USER_ID,preferenceManager.getString(Constants.KEY_USER_ID))
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                if (task.getResult()==null || task.getResult().getDocuments().size()==0){
                                    HashMap<String,Object> tem= new HashMap<>();
                                    tem.put(Constants.KEY_POST_ID,thisPost.id);
                                    tem.put(Constants.KEY_USER_ID,preferenceManager.getString(Constants.KEY_USER_ID));
                                    database.collection(Constants.KEY_COLLECTION_LIKE).add(tem);

                                }
                                else{
                                    database.collection(Constants.KEY_COLLECTION_LIKE).document(task.getResult().getDocuments().get(0).getId()).delete();
                                }
                            }
                        }
                    });

        });
    }

    private void deletePost() {
        database.collection(Constants.KEY_COLLECTION_POSTS).document(thisPost.id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                showToast("Post has been deleted");
            }
        });
    }

    private boolean isValidUser() {
        return valid;
    }

    private void validUser(){
        database.collection(Constants.KEY_COLLECTION_POSTS)
                .whereEqualTo("id",thisPost.id)
                .whereEqualTo(Constants.KEY_USER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .get().addOnCompleteListener(validOnCompleteListener);

    }

    private final OnCompleteListener<QuerySnapshot> validOnCompleteListener = task -> {
        valid= task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0;
    };


    private void hidePost(){
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_POSTS).document(thisPost.id);
        thisPost.visibility= 1- thisPost.visibility;
        if (thisPost.visibility==1){
            showToast("Post will be unhidden");
        }
        else{
            showToast("Post will be hidden");
        }
        documentReference.update(
                Constants.KEY_VISIBILITY, thisPost.visibility
        );


    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}