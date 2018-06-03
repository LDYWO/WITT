package com.db.witt.project_witt;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest (String userEmail, String userPassword, String userGender, String userNickname, String userAge, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userEmail", userEmail);
        parameters.put("userPassword", userPassword);
        parameters.put("userGender", userGender);
        parameters.put("userNickname", userNickname);
        parameters.put("userAge", userAge);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
