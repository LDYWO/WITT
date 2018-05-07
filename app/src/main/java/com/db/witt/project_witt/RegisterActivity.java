package com.db.witt.project_witt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayAdapter adapter;
    Spinner spinner;
    Button registerButton;
    EditText idText;
    EditText textPassword;
    EditText nicknameText;
    RadioGroup genderGroup;
    RadioButton selectedGender;

    private String Uid, userID, userPassword, userNickname, userGender, userAge, userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idText = (EditText)findViewById(R.id.idText);
        textPassword = (EditText)findViewById(R.id.passwordText);
        nicknameText = (EditText)findViewById(R.id.nicknameText);

        genderGroup = (RadioGroup)findViewById(R.id.genderGroup);

        registerButton = (Button)findViewById(R.id.submitButton);
        registerButton.setOnClickListener(this);

        spinner = (Spinner)findViewById(R.id.AgeSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Age, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void CreateUser(){
        // TODO!: 유저 객체 생성 및 DB 등록
    }

    private void registerUser(){
        userID = idText.getText().toString().trim();
        userPassword = textPassword.getText().toString().trim();
        userNickname = nicknameText.getText().toString().trim();

        if (TextUtils.isEmpty(userID)){
            Toast.makeText(this, "ID를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Password를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(userNickname)){
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }

        // TODO!: 회원 가입 인증 구현 및 에러 메세지 출력
    }

    @Override
    public void onClick(View view) {
        registerUser(); // 회원 가입
    }
}
