package com.db.witt.project_witt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    private ArrayList<CustomComment> CommetList = new ArrayList<CustomComment>();

    public CommentAdapter(){}

    @Override
    public int getCount() {
        return CommetList.size();
    }

    @Override
    public Object getItem(int position) {
        return CommetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView userNickname = (TextView) convertView.findViewById(R.id.user_nickname);
        TextView register_time = (TextView) convertView.findViewById(R.id.register_time);
        TextView comment = (TextView) convertView.findViewById(R.id.comment);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        CustomComment commetItem = CommetList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        userNickname.setText(commetItem.getUserNickname());
        register_time.setText(commetItem.getComment_date());
        comment.setText(commetItem.getContent());

        return convertView;
    }

    public void addItem(String usernickname, String content, String comment_date) {
        CustomComment item = new CustomComment();

        item.setUserNickname(usernickname);
        item.setContent(content);
        item.setComment_date(comment_date);

        CommetList.add(item);
    }

    public void removeItem(){
        CommetList.clear();
    }

}


