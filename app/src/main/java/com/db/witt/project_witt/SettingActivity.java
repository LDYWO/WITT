package com.db.witt.project_witt;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        final Button logoutBt = (Button) findViewById(R.id.logoutBt);
        logoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("로그아웃")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setCancelable(false) .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which)
                            {
                                Intent logoutIntent = new Intent(SettingActivity.this, LoginActivity.class); // Intent 전달을 이용한 액티비티 전환
                                SettingActivity.this.startActivity(logoutIntent);
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create(); dialog.show();
            }
        });

        final Button leaveBt = (Button) findViewById(R.id.leaveBt);
        leaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("회원 탈퇴")
                        .setMessage("회원 탈퇴 하시겠습니까?")
                        .setCancelable(false) .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which)
                    {
                        // 회원 탈퇴 Request
                    }
                })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create(); dialog.show();
            }
        });
    }
}
