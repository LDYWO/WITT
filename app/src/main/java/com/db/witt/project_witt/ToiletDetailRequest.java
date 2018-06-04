package com.db.witt.project_witt;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ToiletDetailRequest extends StringRequest {

    final static private String URL = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/ToiletDetail.php";
    private Map<String, String> parameters;

    public ToiletDetailRequest (String toiletname, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("toilet_name", toiletname);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}