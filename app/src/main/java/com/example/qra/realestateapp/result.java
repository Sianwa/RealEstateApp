package com.example.qra.realestateapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class result extends AppCompatActivity {
    private String propertyID;

    ImageView tvImage;
    TextView txtDescription;
   FloatingActionButton vFab;

    private DatabaseReference propertyRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       //prevent keyboard from popping up on activity start
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //ActionBar
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("DETAILS");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        propertyRef= FirebaseDatabase.getInstance().getReference().child("Data");

        propertyID=getIntent().getExtras().get("view_property").toString();


   tvImage =(ImageView)findViewById(R.id.mImage);
   txtDescription=(TextView)findViewById(R.id.mDescription);
   vFab =findViewById(R.id.myfab);

   vFab.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           //sends email request to get agent details
           Intent intent = new Intent(Intent.ACTION_SEND);
           intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"support@realEstate.co.ke"});
           intent.putExtra(Intent.EXTRA_SUBJECT,"Request for Agent Details");
           intent.putExtra(Intent.EXTRA_TEXT,"Hello,I would like to get in touch " +
                   "with one of your agents.Kindly reach out to me using this email.Regards ");
           intent.setType("message/rfc822");
           startActivity(intent);
       }
   });


   RetrievePropertyInfo();
    }

    private void RetrievePropertyInfo() {
        propertyRef.child(propertyID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String propertyImage =dataSnapshot.child("image").getValue().toString();
                String propertyDescription =dataSnapshot.child("description").getValue().toString();

                Picasso.get().load(propertyImage).into(tvImage);
                txtDescription.setText(propertyDescription);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
