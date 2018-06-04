package com.db.witt.project_witt;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LocationRequest extends StringRequest {
    final static private String URL = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/location.php";
    private Map<String, String> parameters;

    public LocationRequest (double latitude, double longitude, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("latitude", String.valueOf(latitude));
        parameters.put("longitude", String.valueOf(longitude));
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
