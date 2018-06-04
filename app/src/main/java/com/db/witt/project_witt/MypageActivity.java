package com.db.witt.project_witt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        final TextView nicknameText = (TextView) findViewById(R.id.userNickname); // userID를 로그인 할 때 Intent로 전달 받아 테이블에서 검색;
        final TextView genderText = (TextView) findViewById(R.id.userGender);
        final TextView ageText = (TextView) findViewById(R.id.userAge);

        final Button editInfoBt = (Button) findViewById(R.id.editUserInfoBt);
        editInfoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 내 정보 수정 액티비티로 이동
                Intent editIntent = new Intent(MypageActivity.this, EditMyInfoActivity.class); // Intent 전달을 이용한 액티비티 전환
                MypageActivity.this.startActivity(editIntent);
                finish();
            }
        });

        final Button settingBt = (Button) findViewById(R.id.settingBt);
        settingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 설정 액티비티로 이동
                Intent settingIntent = new Intent(MypageActivity.this, SettingActivity.class); // Intent 전달을 이용한 액티비티 전환
                MypageActivity.this.startActivity(settingIntent);
                finish();
            }
        });

        final Button myReviewBt = (Button) findViewById(R.id.myreviewBt);
        myReviewBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 내 리뷰 액티비티로 이동
                Intent reviewIntent = new Intent(MypageActivity.this, MyReviewActivity.class); // Intent 전달을 이용한 액티비티 전환
                MypageActivity.this.startActivity(reviewIntent);
                finish();
            }
        });

        final Button myLikeBt = (Button) findViewById(R.id.mylikeBt);
        myLikeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 좋아요 액티비티로 이동
                Intent likeIntent = new Intent(MypageActivity.this, LikeActivity.class); // Intent 전달을 이용한 액티비티 전환
                MypageActivity.this.startActivity(likeIntent);
                finish();
            }
        });
    }
}
