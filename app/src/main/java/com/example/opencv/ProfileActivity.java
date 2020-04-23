package com.example.opencv;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.opencv.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends Fragment {
    ImageView image_profile;
    Button close;
    TextView fullname,bio;

    FirebaseUser firebaseUser;
    String profileid;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREPS", Context.MODE_PRIVATE);
        profileid = prefs.getString ("profileid", "none");

        close = view.findViewById(R.id.close);
        image_profile = view.findViewById(R.id.image_profile);
        fullname = view.findViewById(R.id.fullname);
        bio =view.findViewById(R.id.bio);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });
        return view;


}
    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
          reference.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  if(getContext() == null){
                      return;
                  }
                  User user = dataSnapshot.getValue(User.class);

                  Glide.with(getContext()).load(user.getUsername());
                  fullname.setText(user.getFullname());
                  bio.setText(user.getBio());
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });
    }

}

