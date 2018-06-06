package com.db.witt.project_witt.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainRequest extends StringRequest {

    final static private String URL = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/Toilet_table.php";
    private Map<String, String> parameters;

    public MainRequest (String rating, String latitude, String longitude, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("rating", rating);
        parameters.put("mlatitude", String.valueOf(latitude));
        parameters.put("mlongitude", String.valueOf(longitude));
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
