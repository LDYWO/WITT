package com.example.ldy.project_witt;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText userID;
    EditText userPassword;
    TextView findButton;
    Button loginBt;
    Button registerButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = (EditText)findViewById(R.id.idText);
        userPassword = (EditText)findViewById(R.id.passwordText);

    }




}
