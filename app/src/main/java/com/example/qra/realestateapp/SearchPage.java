package com.example.qra.realestateapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SearchPage extends AppCompatActivity {
    RecyclerView myRecyclerView;
    FirebaseDatabase myFirebasedb;
    DatabaseReference myRef;
    EditText myeditText;
    Button myButton;
    String SearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        //recyclerview
        myRecyclerView = findViewById(R.id.SearchRecycler);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myeditText = (EditText) findViewById(R.id.editText);
        myButton=(Button)findViewById(R.id.Searchbutton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInput = myeditText.getText().toString();
                onStart();
            }
        });

    }
//load recyclerview
    protected void onStart() {
        super.onStart();

        myRef = myFirebasedb.getInstance().getReference().child("Data");

        //set options
        FirebaseRecyclerOptions<SearchModel> options =
                new FirebaseRecyclerOptions.Builder<SearchModel>()
                        .setQuery(myRef.orderByChild("title").startAt(SearchInput).endAt("\u8f88"), SearchModel.class)
                        .build();
        FirebaseRecyclerAdapter<SearchModel, myViewHolder> adapter =
                new FirebaseRecyclerAdapter<SearchModel, myViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull SearchModel model) {
                        holder.mTitleView.setText(model.getTitle());
                        Picasso.get().load(model.getImage()).into(holder.mImageView);

                        //itemClickListener using itemView obj in viewholder
                        //if user clicks anywhere on the item
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String view_property = getRef(position).getKey();

                                Intent intent = new Intent(SearchPage.this, result.class);
                                intent.putExtra("view_property", view_property);
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
                        myViewHolder viewHolder = new myViewHolder(view);
                        return viewHolder;
                    }

                };
        myRecyclerView.setAdapter(adapter);
        adapter.startListening();

    }
    //ViewModel class
    public static class myViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView mTitleView,mDescriptionView;
        ImageView mImageView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            view =itemView;

            //views
            mTitleView = view.findViewById(R.id.mTitle);
            mDescriptionView = view.findViewById(R.id.mDescription);
            mImageView = view.findViewById(R.id.mImage);


        }

    }

}
