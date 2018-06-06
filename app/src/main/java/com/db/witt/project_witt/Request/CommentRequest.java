package com.db.witt.project_witt.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentRequest extends StringRequest {
    final static private String URL = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/Comment.php";
    private Map<String, String> parameters;

    public CommentRequest (String review_id,String userEmail,String toilet_id,String comment_content,String write_date, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("review_id",review_id);
        parameters.put("userEmail",userEmail);
        parameters.put("toilet_id", toilet_id);
        parameters.put("comment_content",comment_content);
        parameters.put("write_date",write_date);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
