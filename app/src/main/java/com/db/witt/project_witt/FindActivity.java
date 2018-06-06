package com.db.witt.project_witt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class FindActivity extends AppCompatActivity implements View.OnClickListener{

    EditText userEmail;
    Button findButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        userEmail = (EditText)findViewById(R.id.idText);

        findButton = (Button)findViewById(R.id.submitButton);
        findButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View view) {
        if (view == findButton){
            progressDialog.setMessage("처리 중 입니다. 잠시 기다려 주세요...");
            progressDialog.show();
            // TODO!: 이메일 인증 및 임시 비밀번호 발급

        }
    }
}
