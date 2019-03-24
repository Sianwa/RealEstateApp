package com.example.qra.realestateapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


public class MainMenu extends AppCompatActivity {
    RecyclerView mRecyclerview;
    FirebaseDatabase mFiredasedb;
    DatabaseReference mRef;
    FloatingActionButton myFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        //FAB
        myFab =findViewById(R.id.fab1);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,SearchPage.class);
                startActivity(intent);
            }
        });

        //ActionBar
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("HOME");
        //recyclerview
        mRecyclerview =findViewById(R.id.recycler);
        mRecyclerview.setHasFixedSize(true);
        //set LinearLayout
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        //send query to firebasedb
        mFiredasedb = FirebaseDatabase.getInstance();
        mRef=mFiredasedb.getReference().child("Data");

    }
    //load data to recyclerView
    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(mRef,Model.class)
                .build();
        FirebaseRecyclerAdapter<Model,ViewHolders>firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Model, ViewHolders>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolders holder, final int position, @NonNull Model model) {
                    holder.mTitleView.setText(model.getTitle());
                    Picasso.get().load(model.getImage()).into(holder.mImageView);
               //TODO holder.mDescriptionView.setText((model.getDescription()));


                    //itemClickListener using itemView obj in viewholder
                        //if user clicks anywhere on the item
                       holder.itemView.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               String view_property = getRef(position).getKey();

                               Intent intent =new Intent(MainMenu.this,result.class);
                               intent.putExtra("view_property",view_property);
                               startActivity(intent);
                           }
                       });
                    }

                    @NonNull
                    @Override
                    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row,viewGroup,false);
                        ViewHolders viewHolder = new ViewHolders(view);
                        return viewHolder;
                    }
                };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    //viewHolder class
    public static class ViewHolders extends RecyclerView.ViewHolder{
        View mView;
        TextView mTitleView,mDescriptionView;
        ImageView mImageView;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            //views
            mTitleView = mView.findViewById(R.id.mTitle);
            mDescriptionView = mView.findViewById(R.id.mDescription);
            mImageView = mView.findViewById(R.id.mImage);

        }
    }

    @Override
    public void onBackPressed() {

    }
}
