package com.example.qra.realestateapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {
    EditText _txtemail;
    EditText _txtpass;
    Button _btnlogin;
    TextView _btnreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //referencing username,password editText and textview for singup

        _btnlogin =(Button)findViewById(R.id.buttonLogin);
        //  _btnlogin.setOnClickListener(this);
        _btnreg=(TextView)findViewById(R.id.btnreg) ;

        _txtemail=(EditText) findViewById(R.id.txtemail);
        _txtpass=(EditText) findViewById(R.id.txtpass);


        _btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginPage.this, CreateAccount.class);
                intent.putExtra("EMAIL",_txtemail.getText().toString().trim());
                startActivity(intent);
            }
        });

    }
}
