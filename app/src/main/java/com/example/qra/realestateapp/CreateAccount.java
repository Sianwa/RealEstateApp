package com.example.qra.realestateapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccount extends AppCompatActivity {
    EditText _txtusername,_txtemail,_txtpass;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //referencing EditText widgets and buttons placed inside the xml layout file

        _txtusername = (EditText)findViewById(R.id.txtname);
        _txtemail = (EditText) findViewById(R.id.txtemail);
        _txtpass = (EditText) findViewById(R.id.txtpass);
        Button _btnreg = (Button) findViewById(R.id.btn_reg);
        _btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        mAuth=FirebaseAuth.getInstance();
        //check to see if the user is currently signed in
    }

    private void createAccount() {
        String email=_txtemail.getText().toString();
        String password=_txtpass.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    //checks status of task proceeding it
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(CreateAccount.this, "Signed in",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(CreateAccount.this, MainMenu.class);
                            startActivity(intent);
                        }


                    }
                });
        }
    }

