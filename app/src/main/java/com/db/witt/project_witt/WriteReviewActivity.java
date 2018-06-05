package com.db.witt.project_witt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class WriteReviewActivity extends AppCompatActivity {

    String name;
    String address;
    String open_time;
    String write_date;
    String toilet_id;
    String userEmail;
    String good_review;
    String bad_review;
    String rating;

    TextView name_tv;
    TextView toilet_address_tv;
    TextView open_time_tv;
    RatingBar ratingBar;
    EditText Good_review_edittext;
    EditText Bad_review_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);

        //actionbar 객체 가져오기
        ActionBar actionBar = getSupportActionBar();

        final Intent toilet_intent = getIntent();

        name = toilet_intent.getStringExtra("toilet_name");
        address = toilet_intent.getStringExtra("toilet_address2");
        open_time = toilet_intent.getStringExtra("open_time");
        toilet_id = toilet_intent.getStringExtra("toilet_id");
        userEmail = toilet_intent.getStringExtra("userEmail");

        name_tv = findViewById(R.id.toilet_name);
        toilet_address_tv = findViewById(R.id.toilet_address);
        open_time_tv = findViewById(R.id.toilet_open_time);
        ratingBar = (RatingBar)findViewById(R.id.ratingbar);
        Good_review_edittext = (EditText)findViewById(R.id.good_review_edittext);
        Bad_review_edittext = (EditText)findViewById(R.id.bad_review_edittext);

        name_tv.setText(name);
        toilet_address_tv.setText(address);
        open_time_tv.setText("개방 시간: "+open_time);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });

        //good review text
        Good_review_edittext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        //bad review text
        Bad_review_edittext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                return true;
            }
            case R.id.newPost:{
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(WriteReviewActivity.this, "리뷰가 등록 되었습니다.", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                            else{
                                Toast.makeText(WriteReviewActivity.this, "등록 실패", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd   aa hh:mm:ss");
                write_date = sdf.format(date);

                good_review = Good_review_edittext.getText().toString().trim();
                bad_review = Bad_review_edittext.getText().toString().trim();
                rating = String.valueOf(ratingBar.getRating());

                WriteReviewRequest writeReviewRequest = new WriteReviewRequest(toilet_id,userEmail,rating,good_review,bad_review,write_date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(WriteReviewActivity.this);
                queue.add(writeReviewRequest);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
