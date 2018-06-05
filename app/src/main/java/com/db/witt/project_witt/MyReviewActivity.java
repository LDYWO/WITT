package com.db.witt.project_witt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

public class MyReviewActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/UserReviewList.php?userEmail=" + URLEncoder.encode(MainActivity.userEmail,"UTF-8");
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

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);

                ArrayList<HashMap<String,String>> review_list = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                int count = 0;
                while(count<jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    String user_email = object.getString("review_userEmail");
                    String toilet_name = object.getString("toilet_name");
                    String write_date = object.getString("write_date");
                    String good_review = object.getString("good_review");
                    String bad_review = object.getString("bad_review");
                    String rating = object.getString("review_rating");
                    String review_id = object.getString("review_id");
                    String bitmap_string = object.getString("bitmap_string");

                    HashMap<String, String> reviews = new HashMap<String, String>();

                    reviews.put("user_email", user_email);
                    reviews.put("toilet_name", toilet_name);
                    reviews.put("write_date", write_date);
                    reviews.put("good_content", good_review);
                    reviews.put("bad_content", bad_review);
                    reviews.put("rating", rating);
                    reviews.put("current_userEmail","bb");
                    reviews.put("toilet_id","aa");
                    reviews.put("review_id",review_id);
                    reviews.put("bitmap_string",bitmap_string);
                    reviews.put("my_reviews","my_reviews");

                    review_list.add(reviews);

                    count++;
                }

                if (review_list.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyReviewActivity.this);
                    dialog = builder.setMessage("작성한 리뷰가 없습니다.\n리뷰를 작성해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }

                Review_Detail_Adapter review_detail_adapter = new Review_Detail_Adapter(MyReviewActivity.this,review_list);
                recyclerView.setAdapter(review_detail_adapter);
                review_detail_adapter.notifyDataSetChanged();

            }catch (Exception e){
                e.printStackTrace();
                Log.i("Target:",target);
            }
        }
    }
}
