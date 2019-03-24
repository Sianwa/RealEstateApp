package com.example.qra.realestateapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    EditText _txtemail;
    EditText _txtpass;
    Button _btnlogin;
    TextView _btnreg;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth=FirebaseAuth.getInstance();

        //referencing username,password editText and textview for singup

        _btnlogin =(Button)findViewById(R.id.buttonLogin);
        _btnreg=(TextView)findViewById(R.id.btnreg);

        _txtemail=(EditText) findViewById(R.id.txtemail);
        _txtpass=(EditText) findViewById(R.id.txtpass);


        _btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             login();
            }
        });

                _btnreg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginPage.this, CreateAccount.class);
                        intent.putExtra("EMAIL", _txtemail.getText().toString().trim());
                        startActivity(intent);
                    }
                });

    }

    private void login() {
        String email=_txtemail.getText().toString();
        String password =_txtpass.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginPage.this, "Signed in",Toast.LENGTH_LONG).show();

                        Intent intent =new Intent(LoginPage.this, MainMenu.class);
                        startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(LoginPage.this,"Wrong Credentials",Toast.LENGTH_LONG).show();
                        }


                    }
                });

    }
}
