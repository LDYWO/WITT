package com.db.witt.project_witt.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.db.witt.project_witt.Adapter.Toilet_info_Adapter;
import com.db.witt.project_witt.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class LikeActivity extends AppCompatActivity {

    private AlertDialog dialog;
    RecyclerView recyclerView;

    ArrayList<HashMap<String,String>> like_list = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/UserLikeList.php?userEmail=" + URLEncoder.encode(MainActivity.userEmail,"UTF-8");
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

                int count = 0;
                while(count<jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    String user_email = object.getString("userEmail");
                    String toilet_name = object.getString("toilet_name");
                    String toilet_address2 = object.getString("toilet_address2");
                    String open_time = object.getString("open_time");
                    String rating = object.getString("rating_avg");

                    if(rating=="null")
                        rating="0";

                    HashMap<String, String> likes = new HashMap<String, String>();

                    likes.put("user_email", user_email);
                    likes.put("toilet_name", toilet_name);
                    likes.put("toilet_address2", toilet_address2);
                    likes.put("open_time", open_time);
                    likes.put("rating", rating);
                    likes.put("likes","likes");

                    like_list.add(likes);

                    count++;
                }

                if (like_list.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LikeActivity.this);
                    dialog = builder.setMessage("작성한 리뷰가 없습니다.\n리뷰를 작성해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }

                Toilet_info_Adapter toilet_info_adapter = new Toilet_info_Adapter(LikeActivity.this,like_list);
                recyclerView.setAdapter(toilet_info_adapter);
                toilet_info_adapter.notifyDataSetChanged();

            }catch (Exception e){
                e.printStackTrace();
                Log.i("Target:",target);
            }
        }
    }
}
