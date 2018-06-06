package com.db.witt.project_witt.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.db.witt.project_witt.Activity.DetailAndReviewActivity;
import com.db.witt.project_witt.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Toilet_info_Adapter extends RecyclerView.Adapter<Toilet_info_Adapter.ViewHolder> {
    Context context;
    ArrayList<HashMap<String,String>> toilet_info_List; //화장실 정보 담겨있음

    public Toilet_info_Adapter(Context context, ArrayList<HashMap<String,String>> toilet_info_List) {
        this.context = context;
        this.toilet_info_List = toilet_info_List;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.toilet_info,null);
        return new ViewHolder(v);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int pos = position;
        final HashMap<String,String> noticeItem = toilet_info_List.get(position);

        holder.tv_toilet_address.setText(noticeItem.get("toilet_address2")); //화장실 주소
        holder.tv_toilet_name.setText(noticeItem.get("toilet_name"));//화장실 이름
        holder.tv_open_time.setText(noticeItem.get("open_time")); //여는 시간
        holder.ratingbar.setRating(Float.valueOf(noticeItem.get("rating")));//평점
        holder.rating_num.setText(noticeItem.get("rating")); //평점

        if(noticeItem.get("likes")!="likes"){
            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, DetailAndReviewActivity.class);
                    intent.putExtra("toilet_address2", toilet_info_List.get(pos).get("toilet_address2"));
                    intent.putExtra("toilet_name", toilet_info_List.get(pos).get("toilet_name"));
                    intent.putExtra("open_time", toilet_info_List.get(pos).get("open_time"));
                    intent.putExtra("rating", toilet_info_List.get(pos).get("rating"));
                    intent.putExtra("toilet_id",toilet_info_List.get(pos).get("toilet_id"));
                    intent.putExtra("userEmail",toilet_info_List.get(pos).get("userEmail"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.toilet_info_List.size();
    }
    /** item layout 불러오기 **/
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_toilet_address;
        TextView tv_toilet_name;
        TextView tv_open_time;
        RatingBar ratingbar;
        TextView rating_num;

        CardView cv;

        public ViewHolder(View v) {
            super(v);
            tv_toilet_address = (TextView) v.findViewById(R.id.tv_toilet_address);
            tv_toilet_name = (TextView)v.findViewById(R.id.tv_toilet_name);
            tv_open_time = (TextView) v.findViewById(R.id.tv_open_time);
            ratingbar = (RatingBar) v.findViewById(R.id.ratingbar);
            rating_num = (TextView) v.findViewById(R.id.rating_num);

            cv = (CardView) v.findViewById(R.id.cv);
        }

    }
}
