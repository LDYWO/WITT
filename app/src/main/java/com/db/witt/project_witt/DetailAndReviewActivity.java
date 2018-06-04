package com.db.witt.project_witt;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailAndReviewActivity extends AppCompatActivity {
    JSONArray toilet_json_arr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailandreview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);

        final Intent toilet_intent = getIntent();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    toilet_json_arr = jsonObject.getJSONArray("result");

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
                        String rating = c.getString("rating");

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
        String name = toilet_intent.getStringExtra("toilet_name");
        ToiletDetailRequest toiletDetailRequest = new ToiletDetailRequest(name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DetailAndReviewActivity.this);
        queue.add(toiletDetailRequest);
    }
}
