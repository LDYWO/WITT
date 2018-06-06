package com.db.witt.project_witt.Request;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WriteReviewRequest extends StringRequest {

    final static private String URL = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/Review.php";
    private Map<String, String> parameters;

    public WriteReviewRequest (String bitmapString,String toilet_id, String userEmail,String rating,String good_review, String bad_review,String write_date, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("toilet_id", toilet_id);
        parameters.put("userEmail", userEmail);
        parameters.put("rating", rating);
        parameters.put("good_review", good_review);
        parameters.put("bad_review", bad_review);
        parameters.put("write_date", write_date);
        parameters.put("bitmap_string",bitmapString);
        Log.e("bitmapString::",bitmapString);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
