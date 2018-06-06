package com.db.witt.project_witt.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.db.witt.project_witt.Request.CommentLoadRequest;
import com.db.witt.project_witt.R;
import com.db.witt.project_witt.Request.CommentRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDialog extends Dialog implements View.OnClickListener{

    private static final int LAYOUT = R.layout.dialog_custom;

    JSONArray comment_json_arr = null;

    String userID,userNickname,comment,comment_date;
    CommentAdapter adapter = new CommentAdapter();

    private Context context;
    private EditText comment_textinput;
    private Button comment_send_button;
    private ListView comment_listview;
    String postID,toilet_id,review_id;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }
    public CustomDialog(@NonNull Context context,String postID,String toilet_id,String review_id) {
        super(context);
        this.context = context;
        this.postID = postID;
        this.toilet_id = toilet_id;
        this.review_id = review_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        comment_textinput = (EditText) findViewById(R.id.comment_textinput);
        comment_send_button = (Button) findViewById(R.id.comment_send_button);
        comment_listview = (ListView) findViewById(R.id.comment_listview);

        int color = Color.parseColor("#b8babc");
        comment_textinput.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        comment_send_button.setOnClickListener(this);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    adapter.removeItem();
                    JSONObject jsonObject = new JSONObject(response);
                    comment_json_arr = jsonObject.getJSONArray("result");
                    if(comment_json_arr.length()!=0){
                        for (int i = 0; i < comment_json_arr.length(); i++) {
                            JSONObject c = comment_json_arr.getJSONObject(i);
                            String writer = c.getString("comment_userEmail");
                            String content = c.getString("comment_content");
                            String date = c.getString("comment_write_date");
                            adapter.addItem(writer, content, date);
                            comment_listview.setAdapter(adapter);
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        CommentLoadRequest commentLoadRequest = new CommentLoadRequest(review_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(commentLoadRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comment_send_button:{
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss");
                comment_date = sdf.format(date);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(context, "댓글이 등록 되었습니다.", Toast.LENGTH_SHORT).show();
                                adapter.removeItem();
                                makeComment();
                            }
                            else{
                                Toast.makeText(context, "등록 실패", Toast.LENGTH_SHORT).show();

                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                comment = comment_textinput.getText().toString().trim();
                CommentRequest commentRequest = new CommentRequest(review_id,postID,toilet_id,comment,comment_date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(commentRequest);
            }
            break;
        }
    }

    public void makeComment(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    adapter.removeItem();
                    JSONObject jsonObject = new JSONObject(response);
                    comment_json_arr = jsonObject.getJSONArray("result");
                    if(comment_json_arr.length()!=0){
                        for (int i = 0; i < comment_json_arr.length(); i++) {
                            JSONObject c = comment_json_arr.getJSONObject(i);
                            String writer = c.getString("comment_userEmail");
                            String content = c.getString("comment_content");
                            String date = c.getString("comment_write_date");
                            adapter.addItem(writer, content, date);
                            comment_listview.setAdapter(adapter);
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        CommentLoadRequest commentLoadRequest = new CommentLoadRequest(review_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(commentLoadRequest);
    }
}

