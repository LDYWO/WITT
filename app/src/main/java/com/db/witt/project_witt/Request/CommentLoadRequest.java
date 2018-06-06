package com.db.witt.project_witt.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentLoadRequest extends StringRequest {
    final static private String URL = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/CommentLoad.php";
    private Map<String, String> parameters;

    public CommentLoadRequest (String review_id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("review_id",review_id);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
