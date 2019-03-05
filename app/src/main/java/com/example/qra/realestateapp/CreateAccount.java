package com.example.qra.realestateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccount extends AppCompatActivity {
    EditText _txtusername,_txtemail,_txtpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //referencing EditText widgets and buttons placed inside the xml layout file

        _txtusername = (EditText)findViewById(R.id.txtname);
        _txtemail = (EditText) findViewById(R.id.txtemail);
        _txtpass = (EditText) findViewById(R.id.txtpass);
        Button _btnreg = (Button) findViewById(R.id.btn_reg);
    }
}
