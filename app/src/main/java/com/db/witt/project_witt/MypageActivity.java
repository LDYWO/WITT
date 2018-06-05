package com.db.witt.project_witt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

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

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/UserInfoRequest.php?userEmail=" + URLEncoder.encode(MainActivity.userEmail,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");

                JSONObject object = jsonArray.getJSONObject(0);
                String userGender = object.getString("userGender");
                String userNickname = object.getString("userNickname");
                String userAge = object.getString("userAge");

                TextView genderText = (TextView) findViewById(R.id.userGender);
                TextView nicknameText = (TextView) findViewById(R.id.userNickname);
                TextView ageText = (TextView) findViewById(R.id.userAge);

                genderText.setText(userGender);
                nicknameText.setText(userNickname);
                ageText.setText(userAge);

            }catch (Exception e){
                e.printStackTrace();
                Log.i("Target:",target);
            }
        }
    }
}
