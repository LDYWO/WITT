package com.db.witt.project_witt.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LikeRequest extends StringRequest {

    final static private String URL = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/LikeRequest.php";
    private Map<String, String> parameters;

    public LikeRequest (String userEmail, String toilet_id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userEmail", userEmail);
        parameters.put("toilet_id", toilet_id);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}

