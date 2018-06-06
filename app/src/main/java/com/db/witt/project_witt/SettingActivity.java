package com.db.witt.project_witt;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

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
                                SharedPreferences appData = getSharedPreferences("appData", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = appData.edit();
                                //editor.clear()는 appData에 들어있는 모든 정보를 기기에서 지웁니다.
                                editor.clear();
                                editor.commit();
                                Intent logoutIntent = new Intent(SettingActivity.this, LoginActivity.class); // Intent 전달을 이용한 액티비티 전환
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success){
                                        SharedPreferences appData = getSharedPreferences("appData", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = appData.edit();
                                        //editor.clear()는 appData에 들어있는 모든 정보를 기기에서 지웁니다.
                                        editor.clear();
                                        editor.commit();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                                        AlertDialog dialog = builder.setMessage("탈퇴 되었습니다.")
                                                .setPositiveButton("확인", null)
                                                .create();
                                        dialog.show();
                                        Intent leaveIntent = new Intent(SettingActivity.this, SplashActivity.class); // Intent 전달을 이용한 액티비티 전환
                                        leaveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        leaveIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        SettingActivity.this.startActivity(leaveIntent);
                                        finish();
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        DeleteUserRequest loginRequest = new DeleteUserRequest(MainActivity.userEmail, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                        queue.add(loginRequest);
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
