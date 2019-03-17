package com.example.qra.realestateapp;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MainMenu extends AppCompatActivity {
    RecyclerView mRecyclerview;
    FirebaseDatabase mFiredasedb;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

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
                    protected void onBindViewHolder(@NonNull ViewHolders holder, int position, @NonNull Model model) {
                    holder.mTitleView.setText(model.getTitle());
                    Picasso.get().load(model.getImage()).into(holder.mImageView);
                    holder.mDescriptionView.setText((model.getDescription()));
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
}
