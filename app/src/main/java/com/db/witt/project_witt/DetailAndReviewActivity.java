package com.db.witt.project_witt;

import android.content.Intent;
import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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

import static com.db.witt.project_witt.MainActivity.userEmail;

public class DetailAndReviewActivity extends AppCompatActivity {
    JSONArray toilet_json_arr = null;
    JSONArray review_json_arr = null;
    JSONArray ratingAvg_json_arr = null;
    Review_Detail_Adapter review_detail_adapter;
    RecyclerView recyclerView;

    ArrayList<HashMap<String,String>> review_list = new ArrayList<HashMap<String, String>>();

    private AlertDialog dialog;

    Intent toilet_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailandreview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toilet_intent = getIntent();

        final ToggleButton LikeBt = (ToggleButton)findViewById(R.id.LikeBt);
        LikeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LikeBt.isChecked()){
                    LikeBt.setBackgroundDrawable(getResources().getDrawable(R.drawable.like)); // Toggle 버튼 좋아요 상태로 전환


                    String toilet_id = toilet_intent.getStringExtra("toilet_id");

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailAndReviewActivity.this);
                                    dialog = builder.setMessage("좋아요 리스트에 추가되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailAndReviewActivity.this);
                                    dialog = builder.setMessage("좋아요 리스트 등록에 실패했습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    LikeRequest likeRequest = new LikeRequest(MainActivity.userEmail, toilet_id, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(DetailAndReviewActivity.this);
                    queue.add(likeRequest);
                }else{
                    LikeBt.setBackgroundDrawable(getResources().getDrawable(R.drawable.dislike));

                    Intent toilet_intent = getIntent();
                    String toilet_id = toilet_intent.getStringExtra("toilet_id");

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailAndReviewActivity.this);
                                    dialog = builder.setMessage("좋아요 리스트에서 제거되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailAndReviewActivity.this);
                                    dialog = builder.setMessage("좋아요 리스트로부터 제거에 실패했습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    DislikeRequest dislikeRequest = new DislikeRequest(MainActivity.userEmail, toilet_id, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(DetailAndReviewActivity.this);
                    queue.add(dislikeRequest);

                }
            }
        });

        final Intent toilet_intent = getIntent();

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    toilet_json_arr = jsonObject.getJSONArray("result");
                    review_json_arr = jsonObject.getJSONArray("result2");
                    ratingAvg_json_arr = jsonObject.getJSONArray("result3");
                    JSONObject ratingavg_obj = ratingAvg_json_arr.getJSONObject(0);

                    if(review_json_arr.length()!=0){
                        for (int i = 0; i < review_json_arr.length(); i++) {
                            JSONObject c = review_json_arr.getJSONObject(i);
                            String user_email = c.getString("review_userEmail");
                            String toilet_name = c.getString("toilet_name");
                            String write_date = c.getString("write_date");
                            String good_review = c.getString("good_review");
                            String bad_review = c.getString("bad_review");
                            String rating = c.getString("review_rating");
                            String review_id = c.getString("review_id");
                            String bitmap_string = c.getString("bitmap_string");

                            HashMap<String, String> reviews = new HashMap<String, String>();

                            reviews.put("user_email", user_email);
                            reviews.put("toilet_name", toilet_name);
                            reviews.put("write_date", write_date);
                            reviews.put("good_content", good_review);
                            reviews.put("bad_content", bad_review);
                            reviews.put("rating", rating);
                            reviews.put("current_userEmail",toilet_intent.getStringExtra("userEmail"));
                            reviews.put("toilet_id",toilet_intent.getStringExtra("toilet_id"));
                            reviews.put("review_id",review_id);
                            reviews.put("bitmap_string",bitmap_string);

                            review_list.add(reviews);
                        }
                        review_detail_adapter = new Review_Detail_Adapter(DetailAndReviewActivity.this,review_list);
                        recyclerView.setAdapter(review_detail_adapter);
                        review_detail_adapter.notifyDataSetChanged();
                    }

                    for (int i = 0; i < toilet_json_arr.length(); i++) {
                        JSONObject c = toilet_json_arr.getJSONObject(i);
                        String division = c.getString("division");
                        String address1 = c.getString("toilet_address1");
                        String address2 = c.getString("toilet_address2");
                        String toilet_name = c.getString("toilet_name");
                        String man_toilet_bowl_num = c.getString("man_toilet_bowl_num");
                        String man_urinal_num = c.getString("man_urinal_num");
                        String man_disabled_toilet_bowl_num = c.getString("man_disabled_toilet_bowl_num");
                        String man_disabled_urinal_num = c.getString("man_disabled_urinal_num");
                        String man_child_toilet_bowl_num = c.getString("man_disabled_toilet_bowl_num");
                        String man_child_urinal_num = c.getString("man_disabled_urinal_num");
                        String woman_toilet_bowl_num = c.getString("woman_toilet_bowl_num");
                        String woman_disabled_toilet_bowl_num = c.getString("woman_disabled_toilet_bowl_num");
                        String woman_child_toilet_bowl_num = c.getString("woman_child_toilet_bowl_num");
                        String management_name = c.getString("management_name");
                        String phone_number = c.getString("phone_number");
                        String open_time = c.getString("open_time");
                        String rating;
                        if(ratingavg_obj.getString("review_rating")!="null"){
                            rating = ratingavg_obj.getString("review_rating");
                        }
                        else {
                            rating = "0";
                        }

                        TextView name_tv = findViewById(R.id.toilet_name);
                        TextView toilet_address_tv = findViewById(R.id.toilet_address);
                        TextView open_time_tv = findViewById(R.id.toilet_open_time);
                        TextView rating_tv = findViewById(R.id.rating_num);
                        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingbar);

                        TextView toilet_type_tv = findViewById(R.id.toilet_type_textview);
                        TextView man_toilet_tv = findViewById(R.id.man_toilet_textview);
                        TextView woman_toilet_tv = findViewById(R.id.woman_toilet_textview);
                        TextView management_tv = findViewById(R.id.management_textview);

                        name_tv.setText(toilet_name);
                        toilet_address_tv.setText("도로명주소: "+address1+" / "+"지번주소: "+address2);
                        open_time_tv.setText("개방시간: "+open_time);
                        rating_tv.setText("평점: "+rating);
                        ratingBar.setRating(Float.parseFloat(rating));

                        toilet_type_tv.setText("화장실 유형: "+ division);
                        man_toilet_tv.setText(
                                "남자 대변기 수: "+man_toilet_bowl_num+
                                " / "+"남자 소변기 수: "+man_urinal_num+
                                " / "+"남자 장애인 대변기 수: "+man_disabled_toilet_bowl_num+
                                " / "+"남자 장애인 소변기 수: "+man_disabled_urinal_num+
                                " / "+"남자 어린이 대변기 수: "+man_child_toilet_bowl_num+
                                " / "+"남자 어린이 소변기 수: "+man_child_urinal_num);
                        woman_toilet_tv.setText(
                                "여자 대변기 수: "+woman_toilet_bowl_num+
                                        " / "+"여자 장애인 대변기 수: "+woman_disabled_toilet_bowl_num+
                                        " / "+"여자 어린이 대변기 수: "+woman_child_toilet_bowl_num);

                        management_tv.setText("관리기관명: "+management_name+" / "+"전화번호: "+phone_number);

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        String toilet_id = toilet_intent.getStringExtra("toilet_id");
        ToiletDetailRequest toiletDetailRequest = new ToiletDetailRequest(toilet_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DetailAndReviewActivity.this);
        queue.add(toiletDetailRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailAndReviewActivity.this,WriteReviewActivity.class);
                intent.putExtra("toilet_address2", toilet_intent.getStringExtra("toilet_address2"));
                intent.putExtra("toilet_name", toilet_intent.getStringExtra("toilet_name"));
                intent.putExtra("open_time", toilet_intent.getStringExtra("open_time"));
                intent.putExtra("toilet_id",toilet_intent.getStringExtra("toilet_id"));
                intent.putExtra("userEmail",toilet_intent.getStringExtra("userEmail"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        Intent toiletIntent = getIntent();
        String target;
        String toilet_id = toiletIntent.getStringExtra("toilet_id");

        @Override
        protected void onPreExecute(){
            try {
                target = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/LikeValidate.php?userEmail=" + URLEncoder.encode(MainActivity.userEmail,"UTF-8") + "&toilet_id=" + URLEncoder.encode(toilet_id,"UTF-8");
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
                JSONObject jsonResponse = new JSONObject(result);
                boolean success = jsonResponse.getBoolean("success");

                if(success){
                    ToggleButton LikeBt = (ToggleButton)findViewById(R.id.LikeBt);
                    LikeBt.setChecked(true);
                    LikeBt.setBackgroundDrawable(getResources().getDrawable(R.drawable.like));
                }
                else{
                    ToggleButton LikeBt = (ToggleButton)findViewById(R.id.LikeBt);
                    LikeBt.setChecked(false);
                    LikeBt.setBackgroundDrawable(getResources().getDrawable(R.drawable.dislike));
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.e("실패","설마...");
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(DetailAndReviewActivity.this,MainActivity.class);
        intent.putExtra("toilet_address2", toilet_intent.getStringExtra("toilet_address2"));
        intent.putExtra("toilet_name", toilet_intent.getStringExtra("toilet_name"));
        intent.putExtra("open_time", toilet_intent.getStringExtra("open_time"));
        intent.putExtra("toilet_id",toilet_intent.getStringExtra("toilet_id"));
        intent.putExtra("userEmail",toilet_intent.getStringExtra("userEmail"));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}
