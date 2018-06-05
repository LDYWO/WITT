package com.db.witt.project_witt;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

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

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
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

            cv = (CardView) v.findViewById(R.id.cv);
        }

    }
}
