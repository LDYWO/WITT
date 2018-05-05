package com.example.ldy.project_witt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText userID;
    EditText userPassword;
    TextView findButton;
    Button loginBt;
    Button registerButton;

    private long lastTimeBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = (EditText)findViewById(R.id.idText);
        userPassword = (EditText)findViewById(R.id.passwordText);

        loginBt = (Button)findViewById(R.id.login); // 로그인 버튼
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO!: 로그인 인증 (프로그레스 바 추가)
                // TODO!: 로그인 인증 완료 시 메인 액티비티로 이동, 실패 시 에러 메시지 출력
            }
        });

        registerButton = (Button)findViewById(R.id.Sign_up); // 회원가입 버튼
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class); // Intent 전달을 이용한 액티비티 전환
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        findButton = (TextView)findViewById(R.id.findpasswordButton); // 패스워드 찾기 버튼
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent findIntent = new Intent(LoginActivity.this, FindActivity.class); // Intent 전달을 이용한 액티비티 전환
                LoginActivity.this.startActivity(findIntent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish();
            return;
        }
        Toast.makeText(this,"뒤로가기 버튼을 한번 더 눌러 종료합니다.",Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis(); // 로그인 화면에서 뒤로가기 키를 두번 누르면 앱 종료;
    }
}
