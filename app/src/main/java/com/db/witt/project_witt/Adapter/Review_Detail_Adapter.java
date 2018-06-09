package com.db.witt.project_witt.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.db.witt.project_witt.Activity.MainActivity;
import com.db.witt.project_witt.Activity.MypageActivity;
import com.db.witt.project_witt.R;
import com.db.witt.project_witt.Request.DeleteReviewRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Review_Detail_Adapter extends RecyclerView.Adapter<Review_Detail_Adapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String,String>> Review_List; //리뷰 정보 담겨있음

    public Review_Detail_Adapter(Context context, ArrayList<HashMap<String,String>> Review_List) {
        this.context = context;
        this.Review_List = Review_List;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_detail,null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final int pos = position;
        final HashMap<String,String> noticeItem = Review_List.get(position);

        holder.tv_user_email.setText(noticeItem.get("user_email")); //유저 이메일
        holder.tv_toilet_name.setText(noticeItem.get("toilet_name"));//화장실 이름
        holder.tv_write_date.setText(noticeItem.get("write_date")); //작성 시간
        holder.tv_good_content.setText(noticeItem.get("good_content"));//좋은점 리뷰
        holder.tv_bad_content.setText(noticeItem.get("bad_content"));//아쉬운점 리뷰
        holder.ratingbar.setRating(Float.valueOf(noticeItem.get("rating")));//평점
        holder.rating_num.setText(noticeItem.get("rating")); //평점

        Bitmap decodedBitmap = StringToBitMap(noticeItem.get("bitmap_string"));
        holder.logo_image.setImageBitmap(decodedBitmap);

        if(noticeItem.get("my_reviews")=="my_reviews"){
            holder.comment_img.setVisibility(View.INVISIBLE);
            holder.comment_button.setVisibility(View.INVISIBLE);
            holder.delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("삭제하기")
                            .setMessage("삭제 하시겠습니까?")
                            .setCancelable(false) .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which)
                        {
                            final Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if(success){
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            AlertDialog dialog = builder.setMessage("삭제 되었습니다.")
                                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            Intent intent = new Intent(context, MypageActivity.class); // Intent 전달을 이용한 액티비티 전환
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            context.startActivity(intent);
                                                        }
                                                    })
                                                    .create();
                                            dialog.show();
                                        }
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            DeleteReviewRequest deleteReviewRequest = new DeleteReviewRequest(MainActivity.userEmail,noticeItem.get("review_id"), responseListener);
                            RequestQueue queue = Volley.newRequestQueue(context);
                            queue.add(deleteReviewRequest);
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
        else{
            holder.delete_button.setVisibility(View.INVISIBLE);
            holder.comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialog dialog = new CustomDialog(context,noticeItem.get("current_userEmail"),noticeItem.get("toilet_id"),noticeItem.get("review_id"));
                    dialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.Review_List.size();
    }
    /** item layout 불러오기 **/
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_user_email;
        TextView tv_toilet_name;
        TextView tv_write_date;
        TextView tv_good_content;
        TextView tv_bad_content;
        RatingBar ratingbar;
        TextView rating_num;
        ImageView logo_image,comment_img;

        Button comment_button,delete_button;

        CardView cv;

        public ViewHolder(View v) {
            super(v);
            tv_user_email = (TextView) v.findViewById(R.id.tv_user_email);
            tv_toilet_name = (TextView)v.findViewById(R.id.tv_toilet_name);
            tv_write_date = (TextView) v.findViewById(R.id.tv_write_date);
            tv_good_content=(TextView)v.findViewById(R.id.tv_good_content);
            tv_bad_content=(TextView)v.findViewById(R.id.tv_bad_content);

            ratingbar = (RatingBar) v.findViewById(R.id.ratingbar);
            rating_num = (TextView) v.findViewById(R.id.rating_num);

            logo_image=(ImageView)v.findViewById(R.id.logo_image);
            comment_img = (ImageView)v.findViewById(R.id.comment_img);

            comment_button = (Button)v.findViewById(R.id.comment_button);
            delete_button =(Button)v.findViewById(R.id.delete_button);

            cv = (CardView) v.findViewById(R.id.cv);
        }

    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            Log.e("encodedString::",encodedString);
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            Log.e("error::",e.getMessage());
            e.getMessage();
            return null;
        }
    }

}
