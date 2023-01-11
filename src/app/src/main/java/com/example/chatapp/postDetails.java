package com.example.chatapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;

public class postDetails extends Fragment {

    private FragmentPostDetailsBinding binding;
    private Post thisPost, post;
    private FirebaseFirestore database;
    private PreferenceManager preferenceManager;
    private Boolean valid = false;
    private Boolean isLiked= false;
    CountDownTimer countDownTimer;

    TextView userPostDetailsDur, userPostDetailsDis, userPostDetailsPace;
    ImageView postDetailsMap;


    public static postDetails newInstance() {
        return new postDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        database=FirebaseFirestore.getInstance();
        userPostDetailsDur = view.findViewById(R.id.user_post_details_dur);
        userPostDetailsDis = view.findViewById(R.id.user_post_details_dis);
        userPostDetailsPace = view.findViewById(R.id.user_post_details_pace);
        postDetailsMap = view.findViewById(R.id.post_details_map);
//        validUser();

//        setListeners();

        thisPost = null;

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((fragment_container)getActivity()).setup(toolbar);
        TextView title  = view.findViewById(R.id.title);
        title.setText("Post");
        TextView test = view.findViewById(R.id.post_details_dur);
        post = (Post) getArguments().getSerializable("testing");
        thisPost = post;
        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (thisPost == null){
                    thisPost = post;
                    countDownTimer.start();
                    return;
                }
                setPostInfo();
            }
        }.start();

//        test.setText(post.user_name);

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

                                    binding.postDetailsLikeCnt.setText(Integer.parseInt(binding.postDetailsLikeCnt.getText().toString())+1);
                                }
                                else{
                                    database.collection(Constants.KEY_COLLECTION_LIKE).document(task.getResult().getDocuments().get(0).getId()).delete();
                                    binding.postDetailsLikeCnt.setText(Integer.parseInt(binding.postDetailsLikeCnt.getText().toString())-1);
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

    private void setPostInfo(){
        userPostDetailsDur.setText(toTimeForm(Math.toIntExact(post.duration)));
        userPostDetailsDis.setText(String.valueOf(Math.round(post.distance*10)/10.0));
        userPostDetailsPace.setText(String.valueOf(Math.round(post.pace*10)/10.0));
        postDetailsMap.setImageBitmap(decodeImage(post.bitmap));
    }
    private Bitmap decodeImage(String u){
        byte[] bytes = android.util.Base64.decode(u, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0, bytes.length);
    }

    String toTimeForm(int u){ // second
        String time = String.valueOf(u/3600);
        u%=3600;
        String min = String.valueOf(u/60);
        u%=60;
        String sec = String.valueOf(u);
        while (min.length()<2) min = '0' + min;
        while (sec.length()<2) sec = '0' + sec;
        if (time.charAt(0) == '0')
            return min + " : " + sec;
        return  time + " : " + min + " : " + sec;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}